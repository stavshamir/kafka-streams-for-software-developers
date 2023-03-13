package io.github.stavshamir;

import io.github.stavshamir.api.Api;
import io.github.stavshamir.types.Track;
import io.github.stavshamir.types.feelings.Feeling;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;

import java.util.Map;

public class FeelingsTopology {
    private static final String INPUT_TOPIC = "tracks";
    private static final String OUTPUT_TOPIC = "feelings";
    private static final int THRESHOLD_SECONDS = 30;

    public static Topology buildTopology() {
        var builder = new StreamsBuilder();

        KStream<Void, Track> tracksStream = builder.stream(INPUT_TOPIC);

        KStream<Void, Track> listenedTracksStream = tracksStream
                .peek((key, track) -> System.out.println("Received new track: " + track))
                .filter((key, track) -> track.getSecondsListened() > THRESHOLD_SECONDS);

        Map<String, KStream<Void, Track>> branches = listenedTracksStream
                .split(Named.as("branch-"))
                .branch((key, track) -> !track.isInstrumental(), Branched.as("not-instrumental"))
                .branch((key, track) -> track.isInstrumental(), Branched.as("instrumental"))
                .noDefaultBranch();

        KStream<Void, Feeling> feelingsFromNonInstrumentalTracks = branches.get("branch-non-instrumental")
                .mapValues(Api::getLyrics)
                .flatMapValues(Api::getFeelingsFromLyrics);

        KStream<Void, Feeling> feelingsFromInstrumentalTracks = branches.get("branch-instrumental")
                .flatMapValues(Api::getFeelingsById);

        KStream<Void, Feeling> mergedStream = feelingsFromInstrumentalTracks.merge(feelingsFromNonInstrumentalTracks);
        mergedStream.to(OUTPUT_TOPIC);

        return builder.build();
    }

}

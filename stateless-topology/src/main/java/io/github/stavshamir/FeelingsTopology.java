package io.github.stavshamir;

import io.github.stavshamir.api.Api;
import io.github.stavshamir.serdes.FeelingSerde;
import io.github.stavshamir.serdes.TrackSerde;
import io.github.stavshamir.types.Track;
import io.github.stavshamir.types.feelings.Feeling;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Map;

public class FeelingsTopology {

    public static Topology buildTopology() {
        var builder = new StreamsBuilder();

        KStream<Void, Track> tracksStream = builder.stream("tracks", Consumed.with(Serdes.Void(), new TrackSerde()));

        Map<String, KStream<Void, Track>> branches = tracksStream
                .peek((key, track) -> System.out.println("Received new track: " + track))
                .filter((key, track) -> track.getSecondsListened() > 30)
                .peek((k, v) -> System.out.println("Filtered track: " + v))
                .split(Named.as("branch-"))
                .branch((key, track) -> track.isInstrumental(), Branched.as("instrumental"))
                .branch((key, track) -> !track.isInstrumental(), Branched.as("not-instrumental"))
                .noDefaultBranch();

        KStream<Void, Feeling> instrumentalStream = branches.get("branch-instrumental")
                .peek((key, track) -> System.out.println("Instrumental track: " + track))
                .flatMapValues(track -> Api.getFeelingsById(track.getId()))
                .peek((key, feeling) -> System.out.println(feeling));

        KStream<Void, Feeling> nonInstrumentalStream = branches.get("branch-not-instrumental")
                .peek((key, track) -> System.out.println("Not instrumental track: " + track))
                .mapValues(track -> Api.getLyrics(track.getId()))
                .flatMapValues(Api::getFeelingsFromLyrics)
                .peek((key, feeling) -> System.out.println(feeling));

        instrumentalStream.merge(nonInstrumentalStream)
                .to("feelings", Produced.with(Serdes.Void(), new FeelingSerde()));

        return builder.build();
    }

}

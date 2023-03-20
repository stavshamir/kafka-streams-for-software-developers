package io.github.stavshamir;

import io.github.stavshamir.serdes.TrackSerde;
import io.github.stavshamir.types.Track;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

public class FeelingsTopology {
    private static final String INPUT_TOPIC = "tracks";
    private static final String OUTPUT_TOPIC = "feelings";
    private static final int THRESHOLD_SECONDS = 30;

    public static Topology buildTopology() {
        var builder = new StreamsBuilder();

        KStream<Void, Track> tracksStream = builder.stream(INPUT_TOPIC, Consumed.with(Serdes.Void(), new TrackSerde()));

        KStream<Void, Track> listenedTracksStream = tracksStream
                .peek((key, track) -> System.out.println("Received new track: " + track));

        return builder.build();
    }

}

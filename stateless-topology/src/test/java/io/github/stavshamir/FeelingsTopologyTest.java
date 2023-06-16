package io.github.stavshamir;

import io.github.stavshamir.serdes.FeelingSerde;
import io.github.stavshamir.serdes.TrackSerializer;
import io.github.stavshamir.types.Track;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeelingsTopologyTest {


    @Test
    void name() {
        Topology topology = FeelingsTopology.buildTopology();
        var topologyTestDriver = new TopologyTestDriver(topology);

        var tracksTopic = topologyTestDriver
                .createInputTopic("tracks", new VoidSerializer(), new TrackSerializer());

        var feelingsTopic = topologyTestDriver
                .createOutputTopic("feelings", new VoidDeserializer(), new StringDeserializer());

        tracksTopic.pipeInput(new Track(UUID.randomUUID(), 200, true));
//        feelingsTopic.readValuesToList();
        System.out.println(feelingsTopic.readValuesToList());
    }


}
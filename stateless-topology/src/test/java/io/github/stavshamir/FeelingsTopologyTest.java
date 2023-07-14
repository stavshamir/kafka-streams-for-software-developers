package io.github.stavshamir;

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

    private final Topology feelingsTopology = FeelingsTopology.buildTopology();

    @Test
    void instrumentalTracksWithListeningTimeOverThreshold() {
        // Given an instrumental track with listening time over 30 seconds
        var track = new Track(UUID.randomUUID(), 20, true);

        // When the track is processed
        var topologyTestDriver = new TopologyTestDriver(feelingsTopology);

        var tracksTopic = topologyTestDriver
                .createInputTopic("tracks", new VoidSerializer(), new TrackSerializer());

        var feelingsTopic = topologyTestDriver
                .createOutputTopic("feelings", new VoidDeserializer(), new StringDeserializer());

        tracksTopic.pipeInput(track);

        // Then the feelings queue will have five records
        assertEquals(5, feelingsTopic.getQueueSize());
    }

    @Test
    void instrumentalTracksWithListeningTimeBelowThreshold() {
        // Given an instrumental track with listening time below 30 seconds
        var track = new Track(UUID.randomUUID(), 5, true);

        // When the track is processed

        // Then the feelings queue will be empty
    }

}

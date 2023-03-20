package io.github.stavshamir.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.stavshamir.types.Track;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class TrackDeserializer implements Deserializer<Track> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Track deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, Track.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

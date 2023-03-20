package io.github.stavshamir.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.stavshamir.types.Track;
import org.apache.kafka.common.serialization.Serializer;

public class TrackSerializer implements Serializer<Track> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Track data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

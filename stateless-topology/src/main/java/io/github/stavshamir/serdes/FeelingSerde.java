package io.github.stavshamir.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.stavshamir.types.feelings.Feeling;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class FeelingSerde implements Serde<Feeling> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Serializer<Feeling> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(Map.of(
                        "feeling", data.getClass().getSimpleName(),
                        "value", data.getValue()
                ));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Deserializer<Feeling> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, Feeling.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}

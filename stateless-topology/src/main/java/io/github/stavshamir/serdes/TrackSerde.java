package io.github.stavshamir.serdes;

import io.github.stavshamir.types.Track;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class TrackSerde implements Serde<Track> {

    @Override
    public Serializer<Track> serializer() {
        return new TrackSerializer();
    }

    @Override
    public Deserializer<Track> deserializer() {
        return new TrackDeserializer();
    }

}

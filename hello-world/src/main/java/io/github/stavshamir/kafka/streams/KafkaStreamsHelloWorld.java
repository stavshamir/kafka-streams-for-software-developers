package io.github.stavshamir.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class KafkaStreamsHelloWorld {
    private static final String INPUT_TOPIC = "names";
    private static final String OUTPUT_TOPIC = "greetings";

    public static void main(String[] args) {
        var builder = new StreamsBuilder();

        KStream<Void, String> greetingsStream = builder.stream(INPUT_TOPIC);
        greetingsStream
                .peek((k, v) -> System.out.println("Received new message: " + v))
                .filterNot((k, v) -> v.isBlank())
                .mapValues(v -> "Hello, " + v)
                .peek((k, v) -> System.out.println("Sending greeting: " + v))
                .to(OUTPUT_TOPIC);

        var streams = new KafkaStreams(builder.build(), buildConfiguration());
        streams.start();

        System.out.println("Application started");

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static Properties buildConfiguration() {
        var properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "hello-world");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }
}
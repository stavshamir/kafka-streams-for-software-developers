package io.github.stavshamir;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.List;
import java.util.Properties;

public class StatetfulApplication {

    public static void main(String[] args) {
        createTopics();

        Properties props = buildConfiguration();
        Topology topology = buildTopology();

        var streams = new KafkaStreams(topology, props);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println("Starting");
        streams.start();
    }

    private static Topology buildTopology() {
        var builder = new StreamsBuilder();

        KStream<String, String> stream = builder.stream("words");
        stream.peek((k, v) -> System.out.printf("Incoming record - Key: %s, Value: %s%n", k, v))
                .groupByKey();

        return builder.build();
    }

    private static Properties buildConfiguration() {
        var properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "stateful");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        return properties;
    }

    private static void createTopics() {
        var properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        try (var adminClient = AdminClient.create(properties)) {
            adminClient.createTopics(List.of(
                    new NewTopic("words", 1, (short) 1)
            ));
        }
    }

}
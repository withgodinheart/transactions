package top.desq.transactions.kafkastream.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

import static top.desq.transactions.kafkastream.util.Constants.APPLICATION_NAME;
import static top.desq.transactions.kafkastream.util.Constants.KAFKA_PORT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaProperties {

    public static Properties stream() {
        final Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_PORT);
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());

        return properties;
    }
}

package top.desq.transactions.app.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Properties;

import static top.desq.transactions.app.util.Constants.KAFKA_PORT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaProperties {

    public static Properties producer() {
        final Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_PORT);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        return properties;
    }
}

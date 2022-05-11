package top.desq.transactions.app.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import top.desq.transactions.app.dto.Request;

import java.util.Properties;

import static top.desq.transactions.app.util.Constants.KAFKA_PORT;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaProducer<Integer, Request> producerFactory() {

        // create Producer properties
        final Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_PORT);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        // create the producer
        return new KafkaProducer<>(properties);
    }
}

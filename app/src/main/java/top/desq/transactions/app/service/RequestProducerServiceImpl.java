package top.desq.transactions.app.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import top.desq.transactions.app.config.KafkaProperties;
import top.desq.transactions.app.dto.Request;

import java.util.Objects;

import static top.desq.transactions.app.util.Constants.REQUEST_TOPIC;

@Service
@Slf4j
public class RequestProducerServiceImpl implements RequestProducerService {

    private final KafkaProducer<Integer, Request> producer =
            new KafkaProducer<>(KafkaProperties.producer());

    @Override
    public void produce(final Request rq) {
        final ProducerRecord<Integer, Request> record =
                new ProducerRecord<>(REQUEST_TOPIC, rq.getId(), rq);

        // send data - asynchronous
        producer.send(record, (metadata, exception) -> {
            // executes every time a record is successfully sent or an exception is thrown
            if (Objects.isNull(exception)) {
                log.info("Key {}. Value {}", rq.getId(), rq);
                // the record was successfully sent
                log.info("Received new metadata. \n Topic: {} \n Partition: {} \n Offset: {} \n Timestamp: {}",
                        metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                log.error("Error while producing", exception);
            }
        });
    }

    @Override
    public void close() {
        producer.flush();
        producer.close();
    }
}

package top.desq.transactions.kafkastream.service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Service;
import top.desq.transactions.kafkastream.config.KafkaProperties;
import top.desq.transactions.kafkastream.dto.Request;
import top.desq.transactions.kafkastream.dto.Transaction;
import top.desq.transactions.kafkastream.util.Mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static top.desq.transactions.kafkastream.util.Constants.*;

@Service
@Slf4j
public class RequestStreamServiceImpl implements RequestStreamService {

    private final KafkaStreams kafkaStreams =
            new KafkaStreams(createTopology(), KafkaProperties.stream());


    @Override
    public void stream() {
        kafkaStreams.start();
    }

    @Override
    public void close() {
        kafkaStreams.close();
    }

    private Topology createTopology() {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        final KStream<String, String> inputTopic = streamsBuilder.stream(REQUEST_TOPIC);
        final KStream<String, String> mappedStream = inputTopic.map((k, v) -> proceedTransactions(v));
        mappedStream.to(TRANSACTION_TOPIC);

        return streamsBuilder.build();
    }

    private KeyValue<String, String> proceedTransactions(final String json) {
        final Request rq = Mapper.read(json, new TypeReference<>() {
        });
        final Map<Integer, Integer> resultMap = perform(rq);
        final Transaction t = Transaction.builder()
                .id(rq.getId())
                .map(resultMap)
                .build();

        return KeyValue.pair(String.valueOf(rq.getId()), Mapper.write(t));
    }

    private Map<Integer, Integer> perform(Request rq) {
        final Map<Integer, Integer> map = new HashMap<>(rq.getCount());
        for (int i = 0; i < rq.getCount(); i++) {
            int year = Try.of(() ->
                    ThreadLocalRandom.current().nextInt(rq.getYearFrom(), rq.getYearTo())).getOrElse(MINIMUM_YEAR);
            map.compute(year, (k, v) -> (Objects.isNull(v) ? 1 : v + 1));
            log.info("Complete transaction for beneficiary #{} in {} year", rq.getId(), year);
        }

        return map;
    }
}

package top.desq.transactions.kafkastream.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String REQUEST_TOPIC = "request_topic";
    public static final String TRANSACTION_TOPIC = "transaction_topic";
    public static final String APPLICATION_NAME = "transactions-kafka";
    public static final String KAFKA_PORT = "127.0.0.1:9092";
    public static final int MINIMUM_YEAR = 2001;
}

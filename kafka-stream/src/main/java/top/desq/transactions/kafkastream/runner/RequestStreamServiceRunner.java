package top.desq.transactions.kafkastream.runner;

import top.desq.transactions.kafkastream.service.RequestStreamServiceImpl;

public class RequestStreamServiceRunner {

    public static void main(String[] args) {
        (new RequestStreamServiceImpl()).stream();
    }
}

package top.desq.transactions.app.service;

import top.desq.transactions.app.dto.Request;

public interface RequestProducerService {

    void produce(final Request rq);

    void close();
}

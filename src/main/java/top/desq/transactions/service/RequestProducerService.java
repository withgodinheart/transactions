package top.desq.transactions.service;

import top.desq.transactions.dto.Request;

public interface RequestProducerService {

    void produce(final Request rq);
}

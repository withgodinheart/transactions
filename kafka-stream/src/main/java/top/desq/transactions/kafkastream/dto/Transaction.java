package top.desq.transactions.kafkastream.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Transaction {

    private int id;

    private Map<Integer, Integer> map;
}

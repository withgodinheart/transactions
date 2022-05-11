package top.desq.transactions.kafkastream.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Request {

    @JsonProperty("beneficiary")
    private int id;

    private int yearFrom;

    private int yearTo;

    private int count;
}

package top.desq.transactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Request {

    @JsonProperty("beneficiary")
    private int id;

    private int dateFrom;

    private int dateTo;

    private int count;
}

package top.desq.transactions.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class InfoResponse {

    private Map<Integer, List<Integer>> data;
}

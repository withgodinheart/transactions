package top.desq.transactions.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.desq.transactions.app.dto.InfoResponse;
import top.desq.transactions.app.dto.Request;
import top.desq.transactions.app.service.RequestProducerService;

import static top.desq.transactions.app.util.Helper.getFakeData;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AjaxController {

    private final RequestProducerService requestProducerService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public void add(@RequestBody Request rq) {
        requestProducerService.produce(rq);
    }

    @PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InfoResponse info() {
        return getFakeData();
    }
}

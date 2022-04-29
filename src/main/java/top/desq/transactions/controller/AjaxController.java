package top.desq.transactions.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.desq.transactions.model.dto.InfoResponse;

import java.util.Map;

import static top.desq.transactions.util.Helper.getFakeData;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AjaxController {

    @PostMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public void add(@RequestParam final Map<String, String> map) {
        System.out.println(map);
        Thread.sleep(500);
    }

    @PostMapping(value = "/info",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InfoResponse info(@RequestParam final Map<String, String> map) {
        return getFakeData();
    }
}

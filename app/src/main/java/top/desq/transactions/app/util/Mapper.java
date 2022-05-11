package top.desq.transactions.app.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Mapper {

    public static final ObjectMapper JSON = JsonMapper.builder()
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true).build();

    public static <T> T read(final String json, final TypeReference<T> reference) {
        return Try.of(() -> JSON.readValue(json, reference)).get();
    }

    public static <T> String write(final T obj) {
        return Try.of(() -> JSON.writeValueAsString(obj)).get();
    }
}

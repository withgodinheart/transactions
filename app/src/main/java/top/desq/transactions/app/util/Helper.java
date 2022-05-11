package top.desq.transactions.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import top.desq.transactions.app.dto.InfoResponse;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Helper {

    public static String randomString() {
        return new Random().ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static int randomInt(int origin, int bound) {
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    public static List<Integer> randomIntList(int origin, int bound, int count) {
        final List<Integer> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(randomInt(origin, bound));
        }

        return list;
    }

    public static InfoResponse getFakeData() {
        final Map<Integer, List<Integer>> data = new HashMap<>();
        data.put(1, randomIntList(1, 100, 22));
        data.put(2, randomIntList(1, 100, 22));
        data.put(3, randomIntList(1, 100, 22));

        return InfoResponse.builder().data(data).build();
    }
}

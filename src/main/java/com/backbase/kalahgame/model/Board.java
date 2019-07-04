package com.backbase.kalahgame.model;

import lombok.ToString;

import javax.persistence.Embeddable;
import java.util.Map;
import java.util.stream.IntStream;

import static com.backbase.kalahgame.constants.Constants.*;
import static java.util.Arrays.fill;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Embeddable
@ToString
public class Board {

    private final int[] pits;

    Board() {

        pits = new int[TOTAL_NUMBER_OF_PITS];

        fill(pits, 0, NUMBER_OF_PITS, NUMBER_OF_SEEDS);
        fill(pits, NUMBER_OF_PITS + 1, TOTAL_NUMBER_OF_PITS - 1, NUMBER_OF_SEEDS);
    }

    public Map<Integer, String> getStatus() {

        return IntStream
                .range(1, TOTAL_NUMBER_OF_PITS + 1)
                .boxed()
                .collect(toMap(identity(), i -> Integer.toString(pits[i - 1])));
    }

}

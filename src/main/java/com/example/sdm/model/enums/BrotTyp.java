package com.example.sdm.model.enums;

import java.util.Random;

public enum BrotTyp {
    SCHWARZBROT(10), GRAUBROT(5), WEISSBROT(2);

    public final int tageBisVerfall;

    private static final BrotTyp[] brotTypen = values();

    private BrotTyp(int tageBisVerfall) {
        this.tageBisVerfall = tageBisVerfall;
    }

    private static final Random PRNG = new Random();

    public static BrotTyp randomBrotTyp() {
        return brotTypen[PRNG.nextInt(brotTypen.length)];
    }
}

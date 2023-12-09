package com.example.sdm.model;

import java.util.OptionalDouble;

public record DailyStatistic(
        long numberProductsTotal,
        long numberProductsToBeRemoved,
        OptionalDouble averageDaysUntilVerfall,
        OptionalDouble averageQuality,
        double totalVerkaufswert) {

    @Override
    public String toString() {
        return String.format("%-18s %-18s %-27s %-17s %-30s",
                (numberProductsTotal),
                (numberProductsToBeRemoved),
                averageDaysUntilVerfall.orElse(-1),
                averageQuality.orElse(-1),
                totalVerkaufswert);
    }

}

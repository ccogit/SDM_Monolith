package com.example.sdm.creators;

import com.example.sdm.model.Produkt;
import com.example.sdm.model.Wein;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;


@Component
@NoArgsConstructor
public class WeinCreator extends Creator {

    @Autowired
    Faker faker;
    @Autowired
    Random random;

    @Override
    public Wein erzeugeProdukt() {
        return new Wein();
    }

    @Override
    public Produkt konfiguriere(Produkt produkt) {
        produkt.bezeichnung(faker.name().lastName())
                .startQualitaet(random.nextInt(0, 30))
                .verfallDatum(LocalDate.of(9999, 12, 31))
                .grundpreis(Math.round(100 * random.nextDouble(10, 85)) / 100.00);
        return produkt;
    }

}

package com.example.sdm.creators;

import com.example.sdm.model.Brot;
import com.example.sdm.model.Produkt;
import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@NoArgsConstructor
public class BrotCreator extends Creator {

    @Autowired
    Faker faker;
    @Autowired
    Random random;

    @Override
    public Brot erzeugeProdukt() {
        return new Brot();
    }

    @Override
    public Produkt konfiguriere(Produkt produkt) {
        produkt.setBezeichnung(faker.name().lastName());
        produkt.setStartQualitaet(random.nextInt(0, 30));
        produkt.setGrundpreis(Math.round(100 * random.nextDouble(3, 9)) / 100.00);
        return produkt;
    }

}

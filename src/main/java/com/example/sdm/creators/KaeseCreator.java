package com.example.sdm.creators;

import com.example.sdm.model.Kaese;
import com.example.sdm.model.Produkt;
import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
@NoArgsConstructor
public class KaeseCreator extends Creator {

    @Autowired Faker faker;
    @Autowired Random random;

    @Override
    public Produkt erzeugeProdukt() {
        return new Kaese();
    }

    @Override
    public Produkt konfiguriere(Produkt produkt) {
        produkt.setBezeichnung(faker.name().lastName());
        produkt.setStartQualitaet(random.nextInt(80, 130));
        produkt.setVerfallDatum(LocalDate.now().plusDays(random.nextInt(50, 100)));
        produkt.setGrundpreis(Math.round(100 * random.nextDouble(2, 25)) / 100.00);
        return produkt;
    }

}

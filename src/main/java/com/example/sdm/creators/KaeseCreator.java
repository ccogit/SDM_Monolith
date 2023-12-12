package com.example.sdm.creators;

import com.example.sdm.model.Kaese;
import com.example.sdm.model.Produkt;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
@NoArgsConstructor
public class KaeseCreator extends Creator {

    @Autowired
    Faker faker;
    @Autowired
    Random random;

    @Override
    public Kaese erzeugeProdukt() {
        return new Kaese();
    }

    @Override
    public Produkt konfiguriere(Produkt produkt) {
        produkt.bezeichnung(faker.name().lastName())
                .startQualitaet(random.nextInt(80, 130))
                .verfallDatum(LocalDate.now().plusDays(random.nextInt(50, 100)))
                .grundpreis(Math.round(100 * random.nextDouble(2, 25)) / 100.00);
        return produkt;
    }

}

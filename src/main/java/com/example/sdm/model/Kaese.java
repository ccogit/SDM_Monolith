package com.example.sdm.model;

import com.example.sdm.model.enums.ProduktTyp;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import static com.example.sdm.Starter.tageVergangenSeitLieferung;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Component
public class Kaese extends Produkt {

    @Override
    public ProduktTyp getProduktTyp() {
        return ProduktTyp.KAESE;
    }

    @Override
    /* Käse hat einen tagesaktuellen Preis. */
    /* Der Tagespreis wird durch Qualität bestimmt: Grundpreis + 0,10€ * Qualität */
    public Double getPreisAktuell() {
        return vonAuslageEntfernen() ? 0.0 : Math.round(100 * (getGrundpreis() + 0.1 * getQualitaetAktuell())) / 100.00;
    }

    @Override
    /* Käse verliert täglich einen Qualitätspunkt */
    public int getQualitaetAktuell() {
        return getStartQualitaet() - tageVergangenSeitLieferung;
    }

    @Override
    /* Käse benötigt ein minimales Qualitätsniveau von 30 */
    public int getPunktabweichungVonSollNiveau() {
        return getQualitaetAktuell() - 30;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

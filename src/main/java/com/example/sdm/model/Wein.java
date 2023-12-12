package com.example.sdm.model;

import com.example.sdm.model.enums.ProduktTyp;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.example.sdm.Starter.tageVergangenSeitLieferung;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Component
public class Wein extends Produkt implements Serializable {

    @Override
    public ProduktTyp getProduktTyp() {
        return ProduktTyp.WEIN;
    }

    @Override
    /* Weine verändern ihren Preis nicht, nachdem sie ins Regal eingeräumt wurden. */
    public Double getPreisAktuell() {
        return vonAuslageEntfernen() ? 0.0 :
                Math.round(100 * (getGrundpreis() + 0.1 * getStartQualitaet())) / 100.00;
    }

    @Override
    /* Wein verliert nicht an Qualität, sondern gewinnt ab dem Stichtag alle 10 Tage +1 Qualität hinzu, bis die Qualität 50 erreicht ist. */
    public int getQualitaetAktuell() {
        return Math.min(getStartQualitaet() + tageVergangenSeitLieferung / 10, 50);
    }

    @Override
    /* Für Wein wird jedes nicht- negative Qualitätsniveau akzeptiert. */
    public int getPunktabweichungVonSollNiveau() {
        return getQualitaetAktuell();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}


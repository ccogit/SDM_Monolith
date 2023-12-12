package com.example.sdm.model;

import com.example.sdm.Starter;
import com.example.sdm.model.enums.BrotTyp;
import com.example.sdm.model.enums.ProduktTyp;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@Component
public class Brot extends Produkt {

    public Brot() {
        this.brotTyp = BrotTyp.randomBrotTyp();
        this.verfallDatum(Starter.lieferDatum.plusDays(brotTyp.tageBisVerfall));
    }

    private BrotTyp brotTyp;

    @Override
    public ProduktTyp getProduktTyp() {
        return ProduktTyp.BROT;
    }

    public LocalDate getVerfallDatum() {
        return lieferDatum().plusDays(getBrotTyp().tageBisVerfall);
    }

    @Override
    /*  Brot kann zwei Preise annehmen:
        - Der reguläre Preis berechnet sich als Grundpreis + 0,1 * aktuelleQualität.
          Da Brot seine Qualität nicht ändert, entspricht die aktuelle Qualität der startQualität.
          Der reguläre Preis gilt bis zum letzten Tag vor Erreichen des Verfalldatums.
        - Der zweite Preis gilt nur am letzten Tag vor Erreichen des Verfalldatums. Er entspricht der Hälfte des regulären Preises */
    public Double getPreisAktuell() {
        double regulaererPreis = Math.round(100 * (grundpreis() + 0.1 * startQualitaet())) / 100.00;
        return getTageBisVerfall() > 1 ? regulaererPreis : regulaererPreis / 2;
    }

    @Override
    /* Brot ändert seine Qualität nicht. */
    public int getQualitaetAktuell() {
        return startQualitaet();
    }

    @Override
    /* Für Brot wird jedes nicht-negative Qualitätsniveau akzeptiert. */
    public int getPunktabweichungVonSollNiveau() {
        return getQualitaetAktuell();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

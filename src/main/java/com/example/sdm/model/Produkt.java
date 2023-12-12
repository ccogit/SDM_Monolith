package com.example.sdm.model;

import com.example.sdm.Starter;
import com.example.sdm.model.enums.ProduktTyp;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.example.sdm.Starter.tageVergangenSeitLieferung;

@Data
@Accessors(fluent = true, chain = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @CsvBindByName(column = "ID")
    private Long id;

    @CsvBindByName(column = "BEZEICHNUNG")
    private String bezeichnung;

    @CsvBindByName(column = "START_QUALITAET")
    private Integer startQualitaet;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "LIEFER_DATUM")
    @Builder.Default
    private LocalDate lieferDatum = Starter.lieferDatum;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "VERFALL_DATUM")
    private LocalDate verfallDatum;

    @CsvBindByName(column = "GRUNDPREIS")
    private Double grundpreis;

    @CsvBindByName(column = "AUSLIEGEND")
    @Builder.Default
    private Boolean ausliegend = true;

    public abstract Double getPreisAktuell();

    public abstract int getQualitaetAktuell();

    public abstract int getPunktabweichungVonSollNiveau();

    public abstract ProduktTyp getProduktTyp();

    public Boolean qualitaetsniveauUnterschritten() {
        return getPunktabweichungVonSollNiveau() < 0;
    }

    /* Käse hat ein Verfallsdatum */
    /* Wein verfällt nicht (VerfallDatum für alle Weine 31.12.9999 (s. WeinCreator)) */
    public Integer getTageBisVerfall() {
        return Math.toIntExact(ChronoUnit.DAYS.between(lieferDatum().plusDays(tageVergangenSeitLieferung), verfallDatum()));
    }

    public Boolean verfallDatumErreicht() {
        return getTageBisVerfall() <= 0;
    }

    /* Bei Unterschreitung eines bestimmten Qualitätsniveaus oder Erreichen des Verfallsdatums werden Produkte aus dem Regal entfernt bzw. entsorgt. */
    public boolean vonAuslageEntfernen() {
        return ausliegend && (verfallDatumErreicht() || qualitaetsniveauUnterschritten());
    }

    public String toString() {
        return String.format("%-5s %-17s %-17s %-17s %-17s %-17s %-17s %-17s %-17s %-17s",
                id,
                bezeichnung,
                getQualitaetAktuell(),
                getPunktabweichungVonSollNiveau(),
                verfallDatum,
                getTageBisVerfall(),
                grundpreis,
                getPreisAktuell(),
                ausliegend,
                vonAuslageEntfernen());
    }

}

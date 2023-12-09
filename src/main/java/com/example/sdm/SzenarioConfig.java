package com.example.sdm;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import static com.example.sdm.Starter.tageVergangenSeitLieferung;

/* Festlegung aller Variablen für das Szenario */

@Configuration
@Data
public class SzenarioConfig {

    /* Szenario Parameter */
    public static final int anzahlEinheitenWein = 15; // Gelieferte und in Regale geräumte Einheiten Wein
    public static final int anzahlEinheitenKaese = 10;  // Gelieferte und in Regale geräumte Einheiten Käse
    public static LocalDate lieferDatum = LocalDate.of(2023, 12, 5); //Datum der Lieferung
    public static final int anzahlTageSimulation = 150;     // Anzahl Tage nach Lieferung, die Simuliert werden sollen

    /* CSV */
    public static boolean leseCsvDaten = false; // Sollen beim App-Start Csv-Daten eingelesen werden?
    public static String fileName = "Produkte.csv"; // Berichtstyp mit möglichen Werten: "Langbericht", "Kurzbericht", "VerfallenListe"
    public static String selectedReportTyp = "Langbericht"; // Name der einzulesenden CSV-Datei

    public static LocalDate getAktuellesDatum() {
        return lieferDatum.plusDays(tageVergangenSeitLieferung);
    }

}

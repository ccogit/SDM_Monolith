package com.example.sdm.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

/* Klasse für Einlesen aller CSV-Daten. Nach Prüfung des ProduktTyps Erstellung und Persistierung von Produkten des Typs */
@Data
@ToString
public class ProduktTransfer {
    @CsvBindByName(column = "ID")
    Long id;

    @CsvBindByName(column = "BEZEICHNUNG")
    String bezeichnung;

    @CsvBindByName(column = "START_QUALITAET")
    Integer startQualitaet;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "LIEFER_DATUM")
    LocalDate lieferDatum;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "VERFALL_DATUM")
    LocalDate verfallDatum;

    @CsvBindByName(column = "GRUNDPREIS")
    Double grundpreis;

    @CsvBindByName(column = "AUSLIEGEND")
    Boolean ausliegend;

    @CsvBindByName(column = "PRODUKT_TYP")
    String produktTyp;

}

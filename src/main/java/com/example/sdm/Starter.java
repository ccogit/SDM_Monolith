package com.example.sdm;


import com.example.sdm.Utils.ReportsGenerator;
import com.example.sdm.creators.BrotCreator;
import com.example.sdm.creators.Creator;
import com.example.sdm.creators.KaeseCreator;
import com.example.sdm.creators.WeinCreator;
import com.example.sdm.model.ProduktTransfer;
import com.example.sdm.model.enums.ProduktTyp;
import com.example.sdm.services.ProduktServices;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Profile("!test")
@Data
public class Starter implements CommandLineRunner {

    private final WeinCreator weinCreator;
    private final KaeseCreator kaeseCreator;
    private final BrotCreator brotCreator;
    private final ProduktServices produktServices;
    private final ReportsGenerator reportsGenerator;

    public static int tageVergangenSeitLieferung = 0;

    /* Konfiguration Szenario-Parameter */

    // Zu liefernde Einheiten der ProduktTypen:
    public static final int anzahlEinheitenWein = 15;
    public static final int anzahlEinheitenKaese = 10;
    public static final int anzahlEinheitenBrot = 5;

    // Datum der Lieferung
    public static LocalDate lieferDatum = LocalDate.of(2023, 12, 5);

    // Anzahl zu simulierender Tage nach Lieferung
    public static final int anzahlTageSimulation = 150;

    // Einlesen von CSV-Daten bei Beginn des App-Starts
    public static boolean leseCsvDaten = false;

    // Typ des anzuzeigenden Berichts. Mögliche Werte: "Langbericht", "Kurzbericht", "VerfallenListe"
    public static String selectedReportTyp = "Langbericht";

    // Name der einzulesenden CSV-Datei in 'resources' Verzeichnis. Nur erforderlich falls leseCsvDaten = true
    public static String fileName = "Produkte.csv";

    @Override
    public void run(String... args) throws Exception {

        // Regale mit Wein, Kaese und Brot befuellen
        produktServices.befuelleRegale(getCreatorForProduktTyp("Wein"), anzahlEinheitenWein);
        produktServices.befuelleRegale(getCreatorForProduktTyp("Kaese"), anzahlEinheitenKaese);
        produktServices.befuelleRegale(getCreatorForProduktTyp("Brot"), anzahlEinheitenBrot);

        // Csv-Daten einlesen
        if (leseCsvDaten) readCSV();

        // Bericht ausgeben
        reportsGenerator.printBericht();
    }

    public Creator getCreatorForProduktTyp(String produktTyp) {
        return switch (produktTyp.toLowerCase()) {
            case "wein" -> weinCreator;
            case "kaese" -> kaeseCreator;
            case "brot" -> brotCreator;
            default -> throw new IllegalStateException("Unexpected value: " + produktTyp);
        };
    }

    public void readCSV() throws IOException, URISyntaxException {

        /* Erfasse Bestand bevor Csv-Produkte eingelesen wurden */
        Map<ProduktTyp, Long> bestandVorCsvImport = produktServices.getAnzahlEinheitenJeProduktTyp();

        /* Einlesen der Produkte aus Csv in Interims-Klasse ProduktTransfer */
        CsvToBean<ProduktTransfer> csvToBean =
                new CsvToBeanBuilder<ProduktTransfer>(Files.newBufferedReader(Paths.get(
                        ClassLoader.getSystemResource(fileName).toURI())))
                        .withType(ProduktTransfer.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

        /* Erstellung der eigentlichen Produkte auf Basis der Eigenschaft 'ProduktTyp' der Klasse ProduktTransfer */
        csvToBean.forEach(produktTransfer -> {
            Creator creator = getCreatorForProduktTyp(produktTransfer.getProduktTyp());
            produktServices.save(produktServices.kopiereWerte(creator.erzeugeProdukt(), produktTransfer));
        });

        /* Ausgabe der eingelesenen Produkte per ProduktTyp */
        System.out.println(System.lineSeparator() + "Mittels Csv in Regale Geräumt: " +
                produktServices.getAnzahlEinheitenJeProduktTyp()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                produktTypLongEntry -> produktTypLongEntry.getValue() -
                                        bestandVorCsvImport.get(produktTypLongEntry.getKey()))));
    }

    public static LocalDate getAktuellesDatum() {
        return lieferDatum.plusDays(tageVergangenSeitLieferung);
    }
}

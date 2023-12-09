package com.example.sdm.Utils;


import com.example.sdm.SzenarioConfig;
import com.example.sdm.creators.KaeseCreator;
import com.example.sdm.creators.WeinCreator;
import com.example.sdm.model.Produkt;
import com.example.sdm.model.ProduktTransfer;
import com.example.sdm.services.ProduktServices;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class ScannerUtils {

    private final WeinCreator weinCreator;
    private final KaeseCreator kaeseCreator;
    private final ProduktServices produktServices;

    public void readCSV() throws IOException, URISyntaxException {
        System.out.println("Einheiten vor Csv-Import: " + produktServices.getBestand().stream().collect(groupingBy(Produkt::getProduktTyp, counting())));
        CsvToBean<ProduktTransfer> csvToBean =
                new CsvToBeanBuilder<ProduktTransfer>(Files.newBufferedReader(Paths.get(
                        ClassLoader.getSystemResource(SzenarioConfig.fileName).toURI())))
                        .withType(ProduktTransfer.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

        csvToBean.forEach(produktTransfer -> {
            switch (produktTransfer.getProduktTyp()) {
                case "WEIN" -> produktServices.save(weinCreator.konfiguriere(weinCreator.erzeugeProdukt()));
                case "KAESE" -> produktServices.save(kaeseCreator.konfiguriere(kaeseCreator.erzeugeProdukt()));
            }
        });
        System.out.println("Einheiten nach Csv-Import: " + produktServices.getBestand().stream().collect(groupingBy(Produkt::getProduktTyp, counting())));
    }

}




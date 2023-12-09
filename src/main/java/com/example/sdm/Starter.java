package com.example.sdm;


import com.example.sdm.Utils.ReportsGenerator;
import com.example.sdm.Utils.ScannerUtils;
import com.example.sdm.creators.KaeseCreator;
import com.example.sdm.creators.WeinCreator;
import com.example.sdm.services.ProduktServices;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.example.sdm.SzenarioConfig.*;

@Component
@Data
public class Starter implements CommandLineRunner {

    private final WeinCreator weinCreator;
    private final KaeseCreator kaeseCreator;
    private final ProduktServices produktServices;
    private final ScannerUtils scannerUtils;
    private final ReportsGenerator reportsGenerator;

    public static int tageVergangenSeitLieferung = 0;

    @Override
    public void run(String... args) throws Exception {
        produktServices.deleteBestand();
        produktServices.befuelleRegale(weinCreator, anzahlEinheitenWein); // Regale mit Wein befuellen
        produktServices.befuelleRegale(kaeseCreator, anzahlEinheitenKaese);  //  Regale mit Kaese befuellen
        if (leseCsvDaten) scannerUtils.readCSV();
        reportsGenerator.printBericht();
    }

}

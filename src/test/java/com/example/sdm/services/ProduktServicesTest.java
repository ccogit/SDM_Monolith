package com.example.sdm.services;

import com.example.sdm.creators.BrotCreator;
import com.example.sdm.creators.KaeseCreator;
import com.example.sdm.creators.WeinCreator;
import com.example.sdm.model.Wein;
import com.example.sdm.model.enums.ProduktTyp;
import com.example.sdm.repositories.ProduktRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProduktServicesTest {

    @Autowired
    WeinCreator weinCreator;
    @Autowired
    KaeseCreator kaeseCreator;
    @Autowired
    BrotCreator brotCreator;
    @Autowired
    ProduktRepository produktRepository;
    @Autowired
    ProduktServices produktServices;

    @Test
    @Order(1)
    void erzeugeWeinViaCreator() {
        Wein wein = weinCreator.erzeugeProdukt();
        assertEquals(new Wein(), wein);
        assertEquals(ProduktTyp.WEIN, wein.getProduktTyp());
    }

    @Test
    @Order(2)
    void konfiguriereWeinViaCreator() {
        Wein wein = weinCreator.erzeugeProdukt();
        assertNull(wein.grundpreis());
        weinCreator.konfiguriere(wein);
        assertNotNull(wein.grundpreis());
    }

    @Test
    @Order(3)
    void getBestandViaRepo() {
        produktRepository.save(weinCreator.erzeugeProdukt());
        produktRepository.save(weinCreator.erzeugeProdukt());
        assertEquals(2, produktRepository.findAll().size());
    }

    @Test
    @Order(4)
    void deleteAllProdukte() {
        assertEquals(2, produktRepository.findAll().size());
        produktRepository.deleteAll();
        assertEquals(0, produktRepository.findAll().size());
    }

    @Test
    @Order(4)
    void getBestandViaProduktServices() {
        produktRepository.save(weinCreator.erzeugeProdukt());
        assertEquals(1, produktServices.getBestand().size());
    }

    @Test
    @Order(5)
    void befuelleRegalViaProduktServices() {
        produktRepository.deleteAll();
        produktServices.befuelleRegale(weinCreator, 5);
        assertEquals(5, produktServices.getBestand().size());
    }

    @Test
    @Order(6)
    void getAnzahlEinheitenJeProduktTyp() {
        produktRepository.deleteAll();
        produktServices.befuelleRegale(weinCreator, 5);
        produktServices.befuelleRegale(kaeseCreator, 5);
        Map<ProduktTyp, Long> einheitenJeProduktTyp = new HashMap<>();
        einheitenJeProduktTyp.put(ProduktTyp.KAESE, 5L);
        einheitenJeProduktTyp.put(ProduktTyp.WEIN, 5L);
        assertEquals(einheitenJeProduktTyp, produktServices.getAnzahlEinheitenJeProduktTyp());
    }

}
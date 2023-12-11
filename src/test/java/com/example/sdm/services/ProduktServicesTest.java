package com.example.sdm.services;

import com.example.sdm.model.Kaese;
import com.example.sdm.model.enums.ProduktTyp;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static com.example.sdm.Starter.anzahlEinheitenKaese;
import static com.example.sdm.Starter.anzahlEinheitenWein;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProduktServicesTest {

    @Autowired
    private ProduktServices produktServices;

    @Test
    @Order(1)
    void getBestand() {
        assertEquals(anzahlEinheitenWein + anzahlEinheitenKaese, produktServices.getBestand().size());
    }

    @Test
    @Order(2)
    void getAnzahlEinheitenJeProduktTyp() {
        Map<ProduktTyp, Long> einheitenJeProduktTyp = new HashMap<>();
        einheitenJeProduktTyp.put(ProduktTyp.KAESE, (long) anzahlEinheitenKaese);
        einheitenJeProduktTyp.put(ProduktTyp.WEIN, (long) anzahlEinheitenWein);
        assertEquals(einheitenJeProduktTyp, produktServices.getAnzahlEinheitenJeProduktTyp());
    }

    @Test
    @Order(3)
    void save() {
        assertNotNull(produktServices.save(Kaese.builder().bezeichnung("TestProdukt").build()).getId());
    }

}
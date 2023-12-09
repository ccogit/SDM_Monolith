package com.example.sdm;

import com.example.sdm.services.ProduktServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SdmApplicationTests {

    @Autowired
    ProduktServices produktServices;

    @Test
    void contextLoads() {
        assertNotNull(produktServices);
    }




}

package com.example.sdm.config;


import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class Configs {

    @Bean
    public Faker dataFaker() {
        return new Faker();
    }

    @Bean
    public Random random() {
        return new Random();
    }

}

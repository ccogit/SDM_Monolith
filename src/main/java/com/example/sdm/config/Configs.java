package com.example.sdm.config;


import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class Configs {

    @Bean
    public Faker faker() {
        return new Faker();
    }

    @Bean
    public Random random(){return new Random();}

}

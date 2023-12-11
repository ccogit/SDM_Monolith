package com.example.sdm.creators;

import com.example.sdm.model.Produkt;
import org.springframework.stereotype.Service;

@Service
public abstract class Creator {

    public abstract Produkt erzeugeProdukt();

    public abstract Produkt konfiguriere(Produkt produkt);

}

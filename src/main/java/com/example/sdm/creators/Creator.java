package com.example.sdm.creators;

import com.example.sdm.model.Produkt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class Creator {

    public abstract Produkt erzeugeProdukt();

    public abstract Produkt konfiguriere(Produkt produkt);

}

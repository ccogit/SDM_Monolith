package com.example.sdm.services;

import com.example.sdm.creators.Creator;
import com.example.sdm.model.DailyStatistic;
import com.example.sdm.model.Produkt;
import com.example.sdm.model.ProduktTransfer;
import com.example.sdm.model.enums.ProduktTyp;
import com.example.sdm.repositories.ProduktRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

@Service
@NoArgsConstructor
public class ProduktServices {

    ProduktRepository produktRepository;

    @Autowired
    public ProduktServices(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    public void befuelleRegale(Creator creator, Integer anzahlEinheiten) {
        int anzahlVorher = getBestand() != null ? getBestand().size() : 0;
        IntStream.range(0, anzahlEinheiten).forEach(i ->
                save(creator.konfiguriere(creator.erzeugeProdukt())));
        System.out.println(System.lineSeparator() + (getBestand().size() - anzahlVorher) + " Einheiten in Regale geräumt.");
    }

    public Produkt save(Produkt produkt) {
        return produktRepository.save(produkt);
    }

    public List<Produkt> getBestand() {
        return produktRepository.findAll();
    }

    public Map<ProduktTyp, Long> getAnzahlEinheitenJeProduktTyp() {
        return getBestand().stream()
                .collect(groupingBy(Produkt::getProduktTyp, counting()));
    }

    public Map<ProduktTyp, Map<Boolean, List<Produkt>>> getAktuellenBestandGroupedByTypDetails() {
        return getBestand().stream()
                .collect(groupingBy(Produkt::getProduktTyp,
                        groupingBy(Produkt::vonAuslageEntfernen)));
    }

    public Map<ProduktTyp, List<Produkt>> getRemoveList() {
        return getBestand().stream()
                .filter(Produkt::vonAuslageEntfernen)
                .collect(groupingBy(Produkt::getProduktTyp));
    }

    public Map<ProduktTyp, DailyStatistic> getStatistic() {
        return getBestand().stream()
                .collect(groupingBy(Produkt::getProduktTyp, collectingAndThen(toList(), list ->
                        new DailyStatistic(
                                list.stream().map(Produkt::id).count(),
                                list.stream().filter(Produkt::vonAuslageEntfernen).count(),
                                OptionalDouble.of(Math.round(100 * list.stream().mapToInt(Produkt::getTageBisVerfall).average().orElse(-1)) / 100.00),
                                OptionalDouble.of(Math.round(100 * list.stream().mapToDouble(Produkt::getQualitaetAktuell).average().orElse(-1)) / 100.00),
                                Math.round(100 * list.stream().mapToDouble(Produkt::getPreisAktuell).sum()) / 100.00
                        ))));
    }

    public void removeProdukteZuEntfernen() {
        produktRepository.deleteAll(getBestand().stream().filter(Produkt::vonAuslageEntfernen).toList());
    }

    public Produkt kopiereWerte(Produkt produkt, ProduktTransfer produktTransfer) {
        produkt.bezeichnung(produktTransfer.getBezeichnung());
        produkt.startQualitaet(produktTransfer.getStartQualitaet());
        produkt.grundpreis(produktTransfer.getGrundpreis());
        produkt.verfallDatum(produktTransfer.getVerfallDatum());
        return produkt;
    }

}

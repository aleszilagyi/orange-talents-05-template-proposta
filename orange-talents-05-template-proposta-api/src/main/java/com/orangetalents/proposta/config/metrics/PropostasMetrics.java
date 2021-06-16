package com.orangetalents.proposta.config.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PropostasMetrics {
    private final MeterRegistry meterRegistry;

    public PropostasMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    private Collection<Tag> criaTags(){
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));
        return tags;
    }

    public void propostasContador() {
        Collection<Tag> tags = criaTags();

        Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
        contadorDePropostasCriadas.increment();
    }

    public Timer consultaPropostaTimer(){
        return this.meterRegistry.timer("consulta_proposta", criaTags());
    }
}
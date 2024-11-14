package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NoticiaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Noticia getNoticiaSample1() {
        return new Noticia()
            .id(1L)
            .titulo("titulo1")
            .conteudo("conteudo1")
            .autor("autor1")
            .categoria("categoria1")
            .fonte("fonte1")
            .url("url1")
            .classificacao("classificacao1");
    }

    public static Noticia getNoticiaSample2() {
        return new Noticia()
            .id(2L)
            .titulo("titulo2")
            .conteudo("conteudo2")
            .autor("autor2")
            .categoria("categoria2")
            .fonte("fonte2")
            .url("url2")
            .classificacao("classificacao2");
    }

    public static Noticia getNoticiaRandomSampleGenerator() {
        return new Noticia()
            .id(longCount.incrementAndGet())
            .titulo(UUID.randomUUID().toString())
            .conteudo(UUID.randomUUID().toString())
            .autor(UUID.randomUUID().toString())
            .categoria(UUID.randomUUID().toString())
            .fonte(UUID.randomUUID().toString())
            .url(UUID.randomUUID().toString())
            .classificacao(UUID.randomUUID().toString());
    }
}

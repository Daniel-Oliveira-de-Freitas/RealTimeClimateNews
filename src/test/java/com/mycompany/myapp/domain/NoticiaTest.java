package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.NoticiaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Noticia.class);
        Noticia noticia1 = getNoticiaSample1();
        Noticia noticia2 = new Noticia();
        assertThat(noticia1).isNotEqualTo(noticia2);

        noticia2.setId(noticia1.getId());
        assertThat(noticia1).isEqualTo(noticia2);

        noticia2 = getNoticiaSample2();
        assertThat(noticia1).isNotEqualTo(noticia2);
    }
}

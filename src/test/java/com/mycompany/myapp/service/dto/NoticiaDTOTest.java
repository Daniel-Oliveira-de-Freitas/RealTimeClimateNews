package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NoticiaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticiaDTO.class);
        NoticiaDTO noticiaDTO1 = new NoticiaDTO();
        noticiaDTO1.setId(1L);
        NoticiaDTO noticiaDTO2 = new NoticiaDTO();
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
        noticiaDTO2.setId(noticiaDTO1.getId());
        assertThat(noticiaDTO1).isEqualTo(noticiaDTO2);
        noticiaDTO2.setId(2L);
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
        noticiaDTO1.setId(null);
        assertThat(noticiaDTO1).isNotEqualTo(noticiaDTO2);
    }
}

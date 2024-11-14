package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.NoticiaAsserts.*;
import static com.mycompany.myapp.domain.NoticiaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NoticiaMapperTest {

    private NoticiaMapper noticiaMapper;

    @BeforeEach
    void setUp() {
        noticiaMapper = new NoticiaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNoticiaSample1();
        var actual = noticiaMapper.toEntity(noticiaMapper.toDto(expected));
        assertNoticiaAllPropertiesEquals(expected, actual);
    }
}

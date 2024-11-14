package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Noticia;
import com.mycompany.myapp.service.dto.NoticiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Noticia} and its DTO {@link NoticiaDTO}.
 */
@Mapper(componentModel = "spring")
public interface NoticiaMapper extends EntityMapper<NoticiaDTO, Noticia> {}

package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Noticia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Noticia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {}

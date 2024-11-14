package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.NoticiaRepository;
import com.mycompany.myapp.service.NoticiaService;
import com.mycompany.myapp.service.dto.NoticiaDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Noticia}.
 */
@RestController
@RequestMapping("/api/noticias")
public class NoticiaResource {

    private static final Logger LOG = LoggerFactory.getLogger(NoticiaResource.class);

    private static final String ENTITY_NAME = "noticia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoticiaService noticiaService;

    private final NoticiaRepository noticiaRepository;

    public NoticiaResource(NoticiaService noticiaService, NoticiaRepository noticiaRepository) {
        this.noticiaService = noticiaService;
        this.noticiaRepository = noticiaRepository;
    }

    /**
     * {@code POST  /noticias} : Create a new noticia.
     *
     * @param noticiaDTO the noticiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticiaDTO, or with status {@code 400 (Bad Request)} if the noticia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NoticiaDTO> createNoticia(@RequestBody NoticiaDTO noticiaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Noticia : {}", noticiaDTO);
        if (noticiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        noticiaDTO = noticiaService.save(noticiaDTO);
        return ResponseEntity.created(new URI("/api/noticias/" + noticiaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, noticiaDTO.getId().toString()))
            .body(noticiaDTO);
    }

    /**
     * {@code PUT  /noticias/:id} : Updates an existing noticia.
     *
     * @param id the id of the noticiaDTO to save.
     * @param noticiaDTO the noticiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticiaDTO,
     * or with status {@code 400 (Bad Request)} if the noticiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NoticiaDTO> updateNoticia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NoticiaDTO noticiaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Noticia : {}, {}", id, noticiaDTO);
        if (noticiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        noticiaDTO = noticiaService.update(noticiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticiaDTO.getId().toString()))
            .body(noticiaDTO);
    }

    /**
     * {@code PATCH  /noticias/:id} : Partial updates given fields of an existing noticia, field will ignore if it is null
     *
     * @param id the id of the noticiaDTO to save.
     * @param noticiaDTO the noticiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticiaDTO,
     * or with status {@code 400 (Bad Request)} if the noticiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the noticiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the noticiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NoticiaDTO> partialUpdateNoticia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NoticiaDTO noticiaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Noticia partially : {}, {}", id, noticiaDTO);
        if (noticiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noticiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noticiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NoticiaDTO> result = noticiaService.partialUpdate(noticiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticiaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /noticias} : get all the noticias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticias in body.
     */
    @GetMapping("")
    public List<NoticiaDTO> getAllNoticias() {
        LOG.debug("REST request to get all Noticias");
        return noticiaService.findAll();
    }

    /**
     * {@code GET  /noticias/:id} : get the "id" noticia.
     *
     * @param id the id of the noticiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoticiaDTO> getNoticia(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Noticia : {}", id);
        Optional<NoticiaDTO> noticiaDTO = noticiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticiaDTO);
    }

    /**
     * {@code DELETE  /noticias/:id} : delete the "id" noticia.
     *
     * @param id the id of the noticiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticia(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Noticia : {}", id);
        noticiaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

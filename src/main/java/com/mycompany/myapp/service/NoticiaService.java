package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Noticia;
import com.mycompany.myapp.repository.NoticiaRepository;
import com.mycompany.myapp.service.dto.NoticiaDTO;
import com.mycompany.myapp.service.mapper.NoticiaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Noticia}.
 */
@Service
@Transactional
public class NoticiaService {

    private static final Logger LOG = LoggerFactory.getLogger(NoticiaService.class);

    private final NoticiaRepository noticiaRepository;

    private final NoticiaMapper noticiaMapper;

    public NoticiaService(NoticiaRepository noticiaRepository, NoticiaMapper noticiaMapper) {
        this.noticiaRepository = noticiaRepository;
        this.noticiaMapper = noticiaMapper;
    }

    /**
     * Save a noticia.
     *
     * @param noticiaDTO the entity to save.
     * @return the persisted entity.
     */
    public NoticiaDTO save(NoticiaDTO noticiaDTO) {
        LOG.debug("Request to save Noticia : {}", noticiaDTO);
        Noticia noticia = noticiaMapper.toEntity(noticiaDTO);
        noticia = noticiaRepository.save(noticia);
        return noticiaMapper.toDto(noticia);
    }

    /**
     * Update a noticia.
     *
     * @param noticiaDTO the entity to save.
     * @return the persisted entity.
     */
    public NoticiaDTO update(NoticiaDTO noticiaDTO) {
        LOG.debug("Request to update Noticia : {}", noticiaDTO);
        Noticia noticia = noticiaMapper.toEntity(noticiaDTO);
        noticia = noticiaRepository.save(noticia);
        return noticiaMapper.toDto(noticia);
    }

    /**
     * Partially update a noticia.
     *
     * @param noticiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NoticiaDTO> partialUpdate(NoticiaDTO noticiaDTO) {
        LOG.debug("Request to partially update Noticia : {}", noticiaDTO);

        return noticiaRepository
            .findById(noticiaDTO.getId())
            .map(existingNoticia -> {
                noticiaMapper.partialUpdate(existingNoticia, noticiaDTO);

                return existingNoticia;
            })
            .map(noticiaRepository::save)
            .map(noticiaMapper::toDto);
    }

    /**
     * Get all the noticias.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NoticiaDTO> findAll() {
        LOG.debug("Request to get all Noticias");
        return noticiaRepository.findAll().stream().map(noticiaMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one noticia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NoticiaDTO> findOne(Long id) {
        LOG.debug("Request to get Noticia : {}", id);
        return noticiaRepository.findById(id).map(noticiaMapper::toDto);
    }

    /**
     * Delete the noticia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Noticia : {}", id);
        noticiaRepository.deleteById(id);
    }
}

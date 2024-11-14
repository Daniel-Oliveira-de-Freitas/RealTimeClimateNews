package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.NoticiaAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Noticia;
import com.mycompany.myapp.repository.NoticiaRepository;
import com.mycompany.myapp.service.dto.NoticiaDTO;
import com.mycompany.myapp.service.mapper.NoticiaMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NoticiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NoticiaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AUTOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTOR = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_FONTE = "AAAAAAAAAA";
    private static final String UPDATED_FONTE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICACAO = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/noticias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private NoticiaMapper noticiaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticiaMockMvc;

    private Noticia noticia;

    private Noticia insertedNoticia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createEntity() {
        return new Noticia()
            .titulo(DEFAULT_TITULO)
            .conteudo(DEFAULT_CONTEUDO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
            .autor(DEFAULT_AUTOR)
            .categoria(DEFAULT_CATEGORIA)
            .fonte(DEFAULT_FONTE)
            .url(DEFAULT_URL)
            .classificacao(DEFAULT_CLASSIFICACAO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Noticia createUpdatedEntity() {
        return new Noticia()
            .titulo(UPDATED_TITULO)
            .conteudo(UPDATED_CONTEUDO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .autor(UPDATED_AUTOR)
            .categoria(UPDATED_CATEGORIA)
            .fonte(UPDATED_FONTE)
            .url(UPDATED_URL)
            .classificacao(UPDATED_CLASSIFICACAO);
    }

    @BeforeEach
    public void initTest() {
        noticia = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedNoticia != null) {
            noticiaRepository.delete(insertedNoticia);
            insertedNoticia = null;
        }
    }

    @Test
    @Transactional
    void createNoticia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);
        var returnedNoticiaDTO = om.readValue(
            restNoticiaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NoticiaDTO.class
        );

        // Validate the Noticia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedNoticia = noticiaMapper.toEntity(returnedNoticiaDTO);
        assertNoticiaUpdatableFieldsEquals(returnedNoticia, getPersistedNoticia(returnedNoticia));

        insertedNoticia = returnedNoticia;
    }

    @Test
    @Transactional
    void createNoticiaWithExistingId() throws Exception {
        // Create the Noticia with an existing ID
        noticia.setId(1L);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNoticias() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.saveAndFlush(noticia);

        // Get all the noticiaList
        restNoticiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fonte").value(hasItem(DEFAULT_FONTE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].classificacao").value(hasItem(DEFAULT_CLASSIFICACAO)));
    }

    @Test
    @Transactional
    void getNoticia() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.saveAndFlush(noticia);

        // Get the noticia
        restNoticiaMockMvc
            .perform(get(ENTITY_API_URL_ID, noticia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticia.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.fonte").value(DEFAULT_FONTE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.classificacao").value(DEFAULT_CLASSIFICACAO));
    }

    @Test
    @Transactional
    void getNonExistingNoticia() throws Exception {
        // Get the noticia
        restNoticiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNoticia() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.saveAndFlush(noticia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the noticia
        Noticia updatedNoticia = noticiaRepository.findById(noticia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNoticia are not directly saved in db
        em.detach(updatedNoticia);
        updatedNoticia
            .titulo(UPDATED_TITULO)
            .conteudo(UPDATED_CONTEUDO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .autor(UPDATED_AUTOR)
            .categoria(UPDATED_CATEGORIA)
            .fonte(UPDATED_FONTE)
            .url(UPDATED_URL)
            .classificacao(UPDATED_CLASSIFICACAO);
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(updatedNoticia);

        restNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticiaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNoticiaToMatchAllProperties(updatedNoticia);
    }

    @Test
    @Transactional
    void putNonExistingNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(longCount.incrementAndGet());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noticiaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(longCount.incrementAndGet());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(longCount.incrementAndGet());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoticiaWithPatch() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.saveAndFlush(noticia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the noticia using partial update
        Noticia partialUpdatedNoticia = new Noticia();
        partialUpdatedNoticia.setId(noticia.getId());

        partialUpdatedNoticia.titulo(UPDATED_TITULO).conteudo(UPDATED_CONTEUDO).categoria(UPDATED_CATEGORIA).fonte(UPDATED_FONTE);

        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNoticia))
            )
            .andExpect(status().isOk());

        // Validate the Noticia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNoticiaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedNoticia, noticia), getPersistedNoticia(noticia));
    }

    @Test
    @Transactional
    void fullUpdateNoticiaWithPatch() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.saveAndFlush(noticia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the noticia using partial update
        Noticia partialUpdatedNoticia = new Noticia();
        partialUpdatedNoticia.setId(noticia.getId());

        partialUpdatedNoticia
            .titulo(UPDATED_TITULO)
            .conteudo(UPDATED_CONTEUDO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .autor(UPDATED_AUTOR)
            .categoria(UPDATED_CATEGORIA)
            .fonte(UPDATED_FONTE)
            .url(UPDATED_URL)
            .classificacao(UPDATED_CLASSIFICACAO);

        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoticia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNoticia))
            )
            .andExpect(status().isOk());

        // Validate the Noticia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNoticiaUpdatableFieldsEquals(partialUpdatedNoticia, getPersistedNoticia(partialUpdatedNoticia));
    }

    @Test
    @Transactional
    void patchNonExistingNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(longCount.incrementAndGet());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noticiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(longCount.incrementAndGet());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(noticiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoticia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        noticia.setId(longCount.incrementAndGet());

        // Create the Noticia
        NoticiaDTO noticiaDTO = noticiaMapper.toDto(noticia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoticiaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(noticiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Noticia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoticia() throws Exception {
        // Initialize the database
        insertedNoticia = noticiaRepository.saveAndFlush(noticia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the noticia
        restNoticiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, noticia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return noticiaRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Noticia getPersistedNoticia(Noticia noticia) {
        return noticiaRepository.findById(noticia.getId()).orElseThrow();
    }

    protected void assertPersistedNoticiaToMatchAllProperties(Noticia expectedNoticia) {
        assertNoticiaAllPropertiesEquals(expectedNoticia, getPersistedNoticia(expectedNoticia));
    }

    protected void assertPersistedNoticiaToMatchUpdatableProperties(Noticia expectedNoticia) {
        assertNoticiaAllUpdatablePropertiesEquals(expectedNoticia, getPersistedNoticia(expectedNoticia));
    }
}

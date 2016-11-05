package com.dualion.controldiners.web.rest;

import com.dualion.controldiners.ControlDinersApp;

import com.dualion.controldiners.domain.UsuarisProces;
import com.dualion.controldiners.repository.UsuarisProcesRepository;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.mapper.UsuarisProcesMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsuarisProcesResource REST controller.
 *
 * @see UsuarisProcesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControlDinersApp.class)
public class UsuarisProcesResourceIntTest {

    private static final Float DEFAULT_DINERS = 1F;
    private static final Float UPDATED_DINERS = 2F;

    @Inject
    private UsuarisProcesRepository usuarisProcesRepository;

    @Inject
    private UsuarisProcesMapper usuarisProcesMapper;

    @Inject
    private UsuarisProcesService usuarisProcesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUsuarisProcesMockMvc;

    private UsuarisProces usuarisProces;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsuarisProcesResource usuarisProcesResource = new UsuarisProcesResource();
        ReflectionTestUtils.setField(usuarisProcesResource, "usuarisProcesService", usuarisProcesService);
        this.restUsuarisProcesMockMvc = MockMvcBuilders.standaloneSetup(usuarisProcesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsuarisProces createEntity(EntityManager em) {
        UsuarisProces usuarisProces = new UsuarisProces()
                .diners(DEFAULT_DINERS);
        return usuarisProces;
    }

    @Before
    public void initTest() {
        usuarisProces = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarisProces() throws Exception {
        int databaseSizeBeforeCreate = usuarisProcesRepository.findAll().size();

        // Create the UsuarisProces
        UsuarisProcesDTO usuarisProcesDTO = usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces);

        restUsuarisProcesMockMvc.perform(post("/api/usuaris-proces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisProcesDTO)))
                .andExpect(status().isCreated());

        // Validate the UsuarisProces in the database
        List<UsuarisProces> usuarisProces = usuarisProcesRepository.findAll();
        assertThat(usuarisProces).hasSize(databaseSizeBeforeCreate + 1);
        UsuarisProces testUsuarisProces = usuarisProces.get(usuarisProces.size() - 1);
        assertThat(testUsuarisProces.getDiners()).isEqualTo(DEFAULT_DINERS);
    }

    @Test
    @Transactional
    public void checkDinersIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarisProcesRepository.findAll().size();
        // set the field null
        usuarisProces.setDiners(null);

        // Create the UsuarisProces, which fails.
        UsuarisProcesDTO usuarisProcesDTO = usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces);

        restUsuarisProcesMockMvc.perform(post("/api/usuaris-proces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisProcesDTO)))
                .andExpect(status().isBadRequest());

        List<UsuarisProces> usuarisProces = usuarisProcesRepository.findAll();
        assertThat(usuarisProces).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarisProces() throws Exception {
        // Initialize the database
        usuarisProcesRepository.saveAndFlush(usuarisProces);

        // Get all the usuarisProces
        restUsuarisProcesMockMvc.perform(get("/api/usuaris-proces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(usuarisProces.getId().intValue())))
                .andExpect(jsonPath("$.[*].diners").value(hasItem(DEFAULT_DINERS.doubleValue())));
    }

    @Test
    @Transactional
    public void getUsuarisProces() throws Exception {
        // Initialize the database
        usuarisProcesRepository.saveAndFlush(usuarisProces);

        // Get the usuarisProces
        restUsuarisProcesMockMvc.perform(get("/api/usuaris-proces/{id}", usuarisProces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuarisProces.getId().intValue()))
            .andExpect(jsonPath("$.diners").value(DEFAULT_DINERS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarisProces() throws Exception {
        // Get the usuarisProces
        restUsuarisProcesMockMvc.perform(get("/api/usuaris-proces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarisProces() throws Exception {
        // Initialize the database
        usuarisProcesRepository.saveAndFlush(usuarisProces);
        int databaseSizeBeforeUpdate = usuarisProcesRepository.findAll().size();

        // Update the usuarisProces
        UsuarisProces updatedUsuarisProces = usuarisProcesRepository.findOne(usuarisProces.getId());
        updatedUsuarisProces
                .diners(UPDATED_DINERS);
        UsuarisProcesDTO usuarisProcesDTO = usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(updatedUsuarisProces);

        restUsuarisProcesMockMvc.perform(put("/api/usuaris-proces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisProcesDTO)))
                .andExpect(status().isOk());

        // Validate the UsuarisProces in the database
        List<UsuarisProces> usuarisProces = usuarisProcesRepository.findAll();
        assertThat(usuarisProces).hasSize(databaseSizeBeforeUpdate);
        UsuarisProces testUsuarisProces = usuarisProces.get(usuarisProces.size() - 1);
        assertThat(testUsuarisProces.getDiners()).isEqualTo(UPDATED_DINERS);
    }

    @Test
    @Transactional
    public void deleteUsuarisProces() throws Exception {
        // Initialize the database
        usuarisProcesRepository.saveAndFlush(usuarisProces);
        int databaseSizeBeforeDelete = usuarisProcesRepository.findAll().size();

        // Get the usuarisProces
        restUsuarisProcesMockMvc.perform(delete("/api/usuaris-proces/{id}", usuarisProces.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UsuarisProces> usuarisProces = usuarisProcesRepository.findAll();
        assertThat(usuarisProces).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.dualion.controldiners.web.rest;

import com.dualion.controldiners.ControlDinersApp;

import com.dualion.controldiners.domain.Proces;
import com.dualion.controldiners.repository.ProcesRepository;
import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.mapper.ProcesMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProcesResource REST controller.
 *
 * @see ProcesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControlDinersApp.class)
public class ProcesResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATA_INICI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_INICI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATA_INICI_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DATA_INICI);

    private static final Boolean DEFAULT_ESTAT = false;
    private static final Boolean UPDATED_ESTAT = true;

    @Inject
    private ProcesRepository procesRepository;

    @Inject
    private ProcesMapper procesMapper;

    @Inject
    private ProcesService procesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProcesMockMvc;

    private Proces proces;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcesResource procesResource = new ProcesResource();
        ReflectionTestUtils.setField(procesResource, "procesService", procesService);
        this.restProcesMockMvc = MockMvcBuilders.standaloneSetup(procesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proces createEntity(EntityManager em) {
        Proces proces = new Proces()
                .dataInici(DEFAULT_DATA_INICI)
                .estat(DEFAULT_ESTAT);
        return proces;
    }

    @Before
    public void initTest() {
        proces = createEntity(em);
    }

    @Test
    @Transactional
    public void createProces() throws Exception {
        int databaseSizeBeforeCreate = procesRepository.findAll().size();

        // Create the Proces
        ProcesDTO procesDTO = procesMapper.procesToProcesDTO(proces);

        restProcesMockMvc.perform(post("/api/proces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(procesDTO)))
                .andExpect(status().isCreated());

        // Validate the Proces in the database
        List<Proces> proces = procesRepository.findAll();
        assertThat(proces).hasSize(databaseSizeBeforeCreate + 1);
        Proces testProces = proces.get(proces.size() - 1);
        assertThat(testProces.getDataInici()).isEqualTo(DEFAULT_DATA_INICI);
        assertThat(testProces.isEstat()).isEqualTo(DEFAULT_ESTAT);
    }

    @Test
    @Transactional
    public void getAllProces() throws Exception {
        // Initialize the database
        procesRepository.saveAndFlush(proces);

        // Get all the proces
        restProcesMockMvc.perform(get("/api/proces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proces.getId().intValue())))
                .andExpect(jsonPath("$.[*].dataInici").value(hasItem(DEFAULT_DATA_INICI_STR)))
                .andExpect(jsonPath("$.[*].estat").value(hasItem(DEFAULT_ESTAT.booleanValue())));
    }

    @Test
    @Transactional
    public void getProces() throws Exception {
        // Initialize the database
        procesRepository.saveAndFlush(proces);

        // Get the proces
        restProcesMockMvc.perform(get("/api/proces/{id}", proces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proces.getId().intValue()))
            .andExpect(jsonPath("$.dataInici").value(DEFAULT_DATA_INICI_STR))
            .andExpect(jsonPath("$.estat").value(DEFAULT_ESTAT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProces() throws Exception {
        // Get the proces
        restProcesMockMvc.perform(get("/api/proces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProces() throws Exception {
        // Initialize the database
        procesRepository.saveAndFlush(proces);
        int databaseSizeBeforeUpdate = procesRepository.findAll().size();

        // Update the proces
        Proces updatedProces = procesRepository.findOne(proces.getId());
        updatedProces
                .dataInici(UPDATED_DATA_INICI)
                .estat(UPDATED_ESTAT);
        ProcesDTO procesDTO = procesMapper.procesToProcesDTO(updatedProces);

        restProcesMockMvc.perform(put("/api/proces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(procesDTO)))
                .andExpect(status().isOk());

        // Validate the Proces in the database
        List<Proces> proces = procesRepository.findAll();
        assertThat(proces).hasSize(databaseSizeBeforeUpdate);
        Proces testProces = proces.get(proces.size() - 1);
        assertThat(testProces.getDataInici()).isEqualTo(UPDATED_DATA_INICI);
        assertThat(testProces.isEstat()).isEqualTo(UPDATED_ESTAT);
    }

    @Test
    @Transactional
    public void deleteProces() throws Exception {
        // Initialize the database
        procesRepository.saveAndFlush(proces);
        int databaseSizeBeforeDelete = procesRepository.findAll().size();

        // Get the proces
        restProcesMockMvc.perform(delete("/api/proces/{id}", proces.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Proces> proces = procesRepository.findAll();
        assertThat(proces).hasSize(databaseSizeBeforeDelete - 1);
    }
}

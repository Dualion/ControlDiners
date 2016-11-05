package com.dualion.controldiners.web.rest;

import com.dualion.controldiners.ControlDinersApp;

import com.dualion.controldiners.domain.Quantitat;
import com.dualion.controldiners.repository.QuantitatRepository;
import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.mapper.QuantitatMapper;

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
 * Test class for the QuantitatResource REST controller.
 *
 * @see QuantitatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControlDinersApp.class)
public class QuantitatResourceIntTest {

    private static final Float DEFAULT_DINERS = 1F;
    private static final Float UPDATED_DINERS = 2F;

    private static final Boolean DEFAULT_ACTIU = false;
    private static final Boolean UPDATED_ACTIU = true;

    @Inject
    private QuantitatRepository quantitatRepository;

    @Inject
    private QuantitatMapper quantitatMapper;

    @Inject
    private QuantitatService quantitatService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuantitatMockMvc;

    private Quantitat quantitat;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuantitatResource quantitatResource = new QuantitatResource();
        ReflectionTestUtils.setField(quantitatResource, "quantitatService", quantitatService);
        this.restQuantitatMockMvc = MockMvcBuilders.standaloneSetup(quantitatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quantitat createEntity(EntityManager em) {
        Quantitat quantitat = new Quantitat()
                .diners(DEFAULT_DINERS)
                .actiu(DEFAULT_ACTIU);
        return quantitat;
    }

    @Before
    public void initTest() {
        quantitat = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuantitat() throws Exception {
        int databaseSizeBeforeCreate = quantitatRepository.findAll().size();

        // Create the Quantitat
        QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(quantitat);

        restQuantitatMockMvc.perform(post("/api/quantitats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quantitatDTO)))
                .andExpect(status().isCreated());

        // Validate the Quantitat in the database
        List<Quantitat> quantitats = quantitatRepository.findAll();
        assertThat(quantitats).hasSize(databaseSizeBeforeCreate + 1);
        Quantitat testQuantitat = quantitats.get(quantitats.size() - 1);
        assertThat(testQuantitat.getDiners()).isEqualTo(DEFAULT_DINERS);
        assertThat(testQuantitat.isActiu()).isEqualTo(DEFAULT_ACTIU);
    }

    @Test
    @Transactional
    public void checkDinersIsRequired() throws Exception {
        int databaseSizeBeforeTest = quantitatRepository.findAll().size();
        // set the field null
        quantitat.setDiners(null);

        // Create the Quantitat, which fails.
        QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(quantitat);

        restQuantitatMockMvc.perform(post("/api/quantitats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quantitatDTO)))
                .andExpect(status().isBadRequest());

        List<Quantitat> quantitats = quantitatRepository.findAll();
        assertThat(quantitats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiuIsRequired() throws Exception {
        int databaseSizeBeforeTest = quantitatRepository.findAll().size();
        // set the field null
        quantitat.setActiu(null);

        // Create the Quantitat, which fails.
        QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(quantitat);

        restQuantitatMockMvc.perform(post("/api/quantitats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quantitatDTO)))
                .andExpect(status().isBadRequest());

        List<Quantitat> quantitats = quantitatRepository.findAll();
        assertThat(quantitats).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuantitats() throws Exception {
        // Initialize the database
        quantitatRepository.saveAndFlush(quantitat);

        // Get all the quantitats
        restQuantitatMockMvc.perform(get("/api/quantitats?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(quantitat.getId().intValue())))
                .andExpect(jsonPath("$.[*].diners").value(hasItem(DEFAULT_DINERS.doubleValue())))
                .andExpect(jsonPath("$.[*].actiu").value(hasItem(DEFAULT_ACTIU.booleanValue())));
    }

    @Test
    @Transactional
    public void getQuantitat() throws Exception {
        // Initialize the database
        quantitatRepository.saveAndFlush(quantitat);

        // Get the quantitat
        restQuantitatMockMvc.perform(get("/api/quantitats/{id}", quantitat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quantitat.getId().intValue()))
            .andExpect(jsonPath("$.diners").value(DEFAULT_DINERS.doubleValue()))
            .andExpect(jsonPath("$.actiu").value(DEFAULT_ACTIU.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuantitat() throws Exception {
        // Get the quantitat
        restQuantitatMockMvc.perform(get("/api/quantitats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuantitat() throws Exception {
        // Initialize the database
        quantitatRepository.saveAndFlush(quantitat);
        int databaseSizeBeforeUpdate = quantitatRepository.findAll().size();

        // Update the quantitat
        Quantitat updatedQuantitat = quantitatRepository.findOne(quantitat.getId());
        updatedQuantitat
                .diners(UPDATED_DINERS)
                .actiu(UPDATED_ACTIU);
        QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(updatedQuantitat);

        restQuantitatMockMvc.perform(put("/api/quantitats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(quantitatDTO)))
                .andExpect(status().isOk());

        // Validate the Quantitat in the database
        List<Quantitat> quantitats = quantitatRepository.findAll();
        assertThat(quantitats).hasSize(databaseSizeBeforeUpdate);
        Quantitat testQuantitat = quantitats.get(quantitats.size() - 1);
        assertThat(testQuantitat.getDiners()).isEqualTo(UPDATED_DINERS);
        assertThat(testQuantitat.isActiu()).isEqualTo(UPDATED_ACTIU);
    }

    @Test
    @Transactional
    public void deleteQuantitat() throws Exception {
        // Initialize the database
        quantitatRepository.saveAndFlush(quantitat);
        int databaseSizeBeforeDelete = quantitatRepository.findAll().size();

        // Get the quantitat
        restQuantitatMockMvc.perform(delete("/api/quantitats/{id}", quantitat.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Quantitat> quantitats = quantitatRepository.findAll();
        assertThat(quantitats).hasSize(databaseSizeBeforeDelete - 1);
    }
}

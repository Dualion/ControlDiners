package com.dualion.controldiners.web.rest;

import com.dualion.controldiners.ControlDinersApp;

import com.dualion.controldiners.domain.DinersPot;
import com.dualion.controldiners.repository.DinersPotRepository;
import com.dualion.controldiners.service.DinersPotService;
import com.dualion.controldiners.service.dto.DinersPotDTO;
import com.dualion.controldiners.service.mapper.DinersPotMapper;

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
 * Test class for the DinersPotResource REST controller.
 *
 * @see DinersPotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControlDinersApp.class)
public class DinersPotResourceIntTest {

    private static final Float DEFAULT_DINERS_TOTALS = 1F;
    private static final Float UPDATED_DINERS_TOTALS = 2F;

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATA_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DATA);

    private static final String DEFAULT_DESCRIPCIO = "AAA";
    private static final String UPDATED_DESCRIPCIO = "BBB";

    @Inject
    private DinersPotRepository dinersPotRepository;

    @Inject
    private DinersPotMapper dinersPotMapper;

    @Inject
    private DinersPotService dinersPotService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDinersPotMockMvc;

    private DinersPot dinersPot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DinersPotResource dinersPotResource = new DinersPotResource();
        ReflectionTestUtils.setField(dinersPotResource, "dinersPotService", dinersPotService);
        this.restDinersPotMockMvc = MockMvcBuilders.standaloneSetup(dinersPotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DinersPot createEntity(EntityManager em) {
        DinersPot dinersPot = new DinersPot()
                .dinersTotals(DEFAULT_DINERS_TOTALS)
                .data(DEFAULT_DATA)
                .descripcio(DEFAULT_DESCRIPCIO);
        return dinersPot;
    }

    @Before
    public void initTest() {
        dinersPot = createEntity(em);
    }

    @Test
    @Transactional
    public void createDinersPot() throws Exception {
        int databaseSizeBeforeCreate = dinersPotRepository.findAll().size();

        // Create the DinersPot
        DinersPotDTO dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(dinersPot);

        restDinersPotMockMvc.perform(post("/api/diners-pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dinersPotDTO)))
                .andExpect(status().isCreated());

        // Validate the DinersPot in the database
        List<DinersPot> dinersPots = dinersPotRepository.findAll();
        assertThat(dinersPots).hasSize(databaseSizeBeforeCreate + 1);
        DinersPot testDinersPot = dinersPots.get(dinersPots.size() - 1);
        assertThat(testDinersPot.getDinersTotals()).isEqualTo(DEFAULT_DINERS_TOTALS);
        assertThat(testDinersPot.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDinersPot.getDescripcio()).isEqualTo(DEFAULT_DESCRIPCIO);
    }

    @Test
    @Transactional
    public void checkDinersTotalsIsRequired() throws Exception {
        int databaseSizeBeforeTest = dinersPotRepository.findAll().size();
        // set the field null
        dinersPot.setDinersTotals(null);

        // Create the DinersPot, which fails.
        DinersPotDTO dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(dinersPot);

        restDinersPotMockMvc.perform(post("/api/diners-pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dinersPotDTO)))
                .andExpect(status().isBadRequest());

        List<DinersPot> dinersPots = dinersPotRepository.findAll();
        assertThat(dinersPots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = dinersPotRepository.findAll().size();
        // set the field null
        dinersPot.setData(null);

        // Create the DinersPot, which fails.
        DinersPotDTO dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(dinersPot);

        restDinersPotMockMvc.perform(post("/api/diners-pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dinersPotDTO)))
                .andExpect(status().isBadRequest());

        List<DinersPot> dinersPots = dinersPotRepository.findAll();
        assertThat(dinersPots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcioIsRequired() throws Exception {
        int databaseSizeBeforeTest = dinersPotRepository.findAll().size();
        // set the field null
        dinersPot.setDescripcio(null);

        // Create the DinersPot, which fails.
        DinersPotDTO dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(dinersPot);

        restDinersPotMockMvc.perform(post("/api/diners-pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dinersPotDTO)))
                .andExpect(status().isBadRequest());

        List<DinersPot> dinersPots = dinersPotRepository.findAll();
        assertThat(dinersPots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDinersPots() throws Exception {
        // Initialize the database
        dinersPotRepository.saveAndFlush(dinersPot);

        // Get all the dinersPots
        restDinersPotMockMvc.perform(get("/api/diners-pots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dinersPot.getId().intValue())))
                .andExpect(jsonPath("$.[*].dinersTotals").value(hasItem(DEFAULT_DINERS_TOTALS.doubleValue())))
                .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA_STR)))
                .andExpect(jsonPath("$.[*].descripcio").value(hasItem(DEFAULT_DESCRIPCIO.toString())));
    }

    @Test
    @Transactional
    public void getDinersPot() throws Exception {
        // Initialize the database
        dinersPotRepository.saveAndFlush(dinersPot);

        // Get the dinersPot
        restDinersPotMockMvc.perform(get("/api/diners-pots/{id}", dinersPot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dinersPot.getId().intValue()))
            .andExpect(jsonPath("$.dinersTotals").value(DEFAULT_DINERS_TOTALS.doubleValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA_STR))
            .andExpect(jsonPath("$.descripcio").value(DEFAULT_DESCRIPCIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDinersPot() throws Exception {
        // Get the dinersPot
        restDinersPotMockMvc.perform(get("/api/diners-pots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDinersPot() throws Exception {
        // Initialize the database
        dinersPotRepository.saveAndFlush(dinersPot);
        int databaseSizeBeforeUpdate = dinersPotRepository.findAll().size();

        // Update the dinersPot
        DinersPot updatedDinersPot = dinersPotRepository.findOne(dinersPot.getId());
        updatedDinersPot
                .dinersTotals(UPDATED_DINERS_TOTALS)
                .data(UPDATED_DATA)
                .descripcio(UPDATED_DESCRIPCIO);
        DinersPotDTO dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(updatedDinersPot);

        restDinersPotMockMvc.perform(put("/api/diners-pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dinersPotDTO)))
                .andExpect(status().isOk());

        // Validate the DinersPot in the database
        List<DinersPot> dinersPots = dinersPotRepository.findAll();
        assertThat(dinersPots).hasSize(databaseSizeBeforeUpdate);
        DinersPot testDinersPot = dinersPots.get(dinersPots.size() - 1);
        assertThat(testDinersPot.getDinersTotals()).isEqualTo(UPDATED_DINERS_TOTALS);
        assertThat(testDinersPot.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDinersPot.getDescripcio()).isEqualTo(UPDATED_DESCRIPCIO);
    }

    @Test
    @Transactional
    public void deleteDinersPot() throws Exception {
        // Initialize the database
        dinersPotRepository.saveAndFlush(dinersPot);
        int databaseSizeBeforeDelete = dinersPotRepository.findAll().size();

        // Get the dinersPot
        restDinersPotMockMvc.perform(delete("/api/diners-pots/{id}", dinersPot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DinersPot> dinersPots = dinersPotRepository.findAll();
        assertThat(dinersPots).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.dualion.controldiners.web.rest;

import com.dualion.controldiners.ControlDinersApp;

import com.dualion.controldiners.domain.Pot;
import com.dualion.controldiners.repository.PotRepository;
import com.dualion.controldiners.service.PotService;
import com.dualion.controldiners.service.dto.PotDTO;
import com.dualion.controldiners.service.mapper.PotMapper;

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
 * Test class for the PotResource REST controller.
 *
 * @see PotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControlDinersApp.class)
public class PotResourceIntTest {

    private static final String DEFAULT_NOM = "AAA";
    private static final String UPDATED_NOM = "BBB";

    private static final ZonedDateTime DEFAULT_DATA_INICI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_INICI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATA_INICI_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DATA_INICI);

    @Inject
    private PotRepository potRepository;

    @Inject
    private PotMapper potMapper;

    @Inject
    private PotService potService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPotMockMvc;

    private Pot pot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PotResource potResource = new PotResource();
        ReflectionTestUtils.setField(potResource, "potService", potService);
        this.restPotMockMvc = MockMvcBuilders.standaloneSetup(potResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pot createEntity(EntityManager em) {
        Pot pot = new Pot()
                .nom(DEFAULT_NOM)
                .dataInici(DEFAULT_DATA_INICI);
        return pot;
    }

    @Before
    public void initTest() {
        pot = createEntity(em);
    }

    @Test
    @Transactional
    public void createPot() throws Exception {
        int databaseSizeBeforeCreate = potRepository.findAll().size();

        // Create the Pot
        PotDTO potDTO = potMapper.potToPotDTO(pot);

        restPotMockMvc.perform(post("/api/pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(potDTO)))
                .andExpect(status().isCreated());

        // Validate the Pot in the database
        List<Pot> pots = potRepository.findAll();
        assertThat(pots).hasSize(databaseSizeBeforeCreate + 1);
        Pot testPot = pots.get(pots.size() - 1);
        assertThat(testPot.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPot.getDataInici()).isEqualTo(DEFAULT_DATA_INICI);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = potRepository.findAll().size();
        // set the field null
        pot.setNom(null);

        // Create the Pot, which fails.
        PotDTO potDTO = potMapper.potToPotDTO(pot);

        restPotMockMvc.perform(post("/api/pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(potDTO)))
                .andExpect(status().isBadRequest());

        List<Pot> pots = potRepository.findAll();
        assertThat(pots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPots() throws Exception {
        // Initialize the database
        potRepository.saveAndFlush(pot);

        // Get all the pots
        restPotMockMvc.perform(get("/api/pots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pot.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].dataInici").value(hasItem(DEFAULT_DATA_INICI_STR)));
    }

    @Test
    @Transactional
    public void getPot() throws Exception {
        // Initialize the database
        potRepository.saveAndFlush(pot);

        // Get the pot
        restPotMockMvc.perform(get("/api/pots/{id}", pot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pot.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.dataInici").value(DEFAULT_DATA_INICI_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPot() throws Exception {
        // Get the pot
        restPotMockMvc.perform(get("/api/pots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePot() throws Exception {
        // Initialize the database
        potRepository.saveAndFlush(pot);
        int databaseSizeBeforeUpdate = potRepository.findAll().size();

        // Update the pot
        Pot updatedPot = potRepository.findOne(pot.getId());
        updatedPot
                .nom(UPDATED_NOM)
                .dataInici(UPDATED_DATA_INICI);
        PotDTO potDTO = potMapper.potToPotDTO(updatedPot);

        restPotMockMvc.perform(put("/api/pots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(potDTO)))
                .andExpect(status().isOk());

        // Validate the Pot in the database
        List<Pot> pots = potRepository.findAll();
        assertThat(pots).hasSize(databaseSizeBeforeUpdate);
        Pot testPot = pots.get(pots.size() - 1);
        assertThat(testPot.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPot.getDataInici()).isEqualTo(UPDATED_DATA_INICI);
    }

    @Test
    @Transactional
    public void deletePot() throws Exception {
        // Initialize the database
        potRepository.saveAndFlush(pot);
        int databaseSizeBeforeDelete = potRepository.findAll().size();

        // Get the pot
        restPotMockMvc.perform(delete("/api/pots/{id}", pot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pot> pots = potRepository.findAll();
        assertThat(pots).hasSize(databaseSizeBeforeDelete - 1);
    }
}

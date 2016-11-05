package com.dualion.controldiners.web.rest;

import com.dualion.controldiners.ControlDinersApp;

import com.dualion.controldiners.domain.Usuaris;
import com.dualion.controldiners.repository.UsuarisRepository;
import com.dualion.controldiners.service.UsuarisService;
import com.dualion.controldiners.service.dto.UsuarisDTO;
import com.dualion.controldiners.service.mapper.UsuarisMapper;

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
 * Test class for the UsuarisResource REST controller.
 *
 * @see UsuarisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControlDinersApp.class)
public class UsuarisResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATA_INICI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATA_INICI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATA_INICI_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_DATA_INICI);

    private static final Boolean DEFAULT_ACTIU = false;
    private static final Boolean UPDATED_ACTIU = true;

    @Inject
    private UsuarisRepository usuarisRepository;

    @Inject
    private UsuarisMapper usuarisMapper;

    @Inject
    private UsuarisService usuarisService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUsuarisMockMvc;

    private Usuaris usuaris;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsuarisResource usuarisResource = new UsuarisResource();
        ReflectionTestUtils.setField(usuarisResource, "usuarisService", usuarisService);
        this.restUsuarisMockMvc = MockMvcBuilders.standaloneSetup(usuarisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuaris createEntity(EntityManager em) {
        Usuaris usuaris = new Usuaris()
                .nom(DEFAULT_NOM)
                .email(DEFAULT_EMAIL)
                .dataInici(DEFAULT_DATA_INICI)
                .actiu(DEFAULT_ACTIU);
        return usuaris;
    }

    @Before
    public void initTest() {
        usuaris = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuaris() throws Exception {
        int databaseSizeBeforeCreate = usuarisRepository.findAll().size();

        // Create the Usuaris
        UsuarisDTO usuarisDTO = usuarisMapper.usuarisToUsuarisDTO(usuaris);

        restUsuarisMockMvc.perform(post("/api/usuarises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisDTO)))
                .andExpect(status().isCreated());

        // Validate the Usuaris in the database
        List<Usuaris> usuarises = usuarisRepository.findAll();
        assertThat(usuarises).hasSize(databaseSizeBeforeCreate + 1);
        Usuaris testUsuaris = usuarises.get(usuarises.size() - 1);
        assertThat(testUsuaris.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testUsuaris.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsuaris.getDataInici()).isEqualTo(DEFAULT_DATA_INICI);
        assertThat(testUsuaris.isActiu()).isEqualTo(DEFAULT_ACTIU);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarisRepository.findAll().size();
        // set the field null
        usuaris.setNom(null);

        // Create the Usuaris, which fails.
        UsuarisDTO usuarisDTO = usuarisMapper.usuarisToUsuarisDTO(usuaris);

        restUsuarisMockMvc.perform(post("/api/usuarises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisDTO)))
                .andExpect(status().isBadRequest());

        List<Usuaris> usuarises = usuarisRepository.findAll();
        assertThat(usuarises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarisRepository.findAll().size();
        // set the field null
        usuaris.setEmail(null);

        // Create the Usuaris, which fails.
        UsuarisDTO usuarisDTO = usuarisMapper.usuarisToUsuarisDTO(usuaris);

        restUsuarisMockMvc.perform(post("/api/usuarises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisDTO)))
                .andExpect(status().isBadRequest());

        List<Usuaris> usuarises = usuarisRepository.findAll();
        assertThat(usuarises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIniciIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarisRepository.findAll().size();
        // set the field null
        usuaris.setDataInici(null);

        // Create the Usuaris, which fails.
        UsuarisDTO usuarisDTO = usuarisMapper.usuarisToUsuarisDTO(usuaris);

        restUsuarisMockMvc.perform(post("/api/usuarises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisDTO)))
                .andExpect(status().isBadRequest());

        List<Usuaris> usuarises = usuarisRepository.findAll();
        assertThat(usuarises).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarises() throws Exception {
        // Initialize the database
        usuarisRepository.saveAndFlush(usuaris);

        // Get all the usuarises
        restUsuarisMockMvc.perform(get("/api/usuarises?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(usuaris.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].dataInici").value(hasItem(DEFAULT_DATA_INICI_STR)))
                .andExpect(jsonPath("$.[*].actiu").value(hasItem(DEFAULT_ACTIU.booleanValue())));
    }

    @Test
    @Transactional
    public void getUsuaris() throws Exception {
        // Initialize the database
        usuarisRepository.saveAndFlush(usuaris);

        // Get the usuaris
        restUsuarisMockMvc.perform(get("/api/usuarises/{id}", usuaris.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuaris.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dataInici").value(DEFAULT_DATA_INICI_STR))
            .andExpect(jsonPath("$.actiu").value(DEFAULT_ACTIU.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuaris() throws Exception {
        // Get the usuaris
        restUsuarisMockMvc.perform(get("/api/usuarises/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuaris() throws Exception {
        // Initialize the database
        usuarisRepository.saveAndFlush(usuaris);
        int databaseSizeBeforeUpdate = usuarisRepository.findAll().size();

        // Update the usuaris
        Usuaris updatedUsuaris = usuarisRepository.findOne(usuaris.getId());
        updatedUsuaris
                .nom(UPDATED_NOM)
                .email(UPDATED_EMAIL)
                .dataInici(UPDATED_DATA_INICI)
                .actiu(UPDATED_ACTIU);
        UsuarisDTO usuarisDTO = usuarisMapper.usuarisToUsuarisDTO(updatedUsuaris);

        restUsuarisMockMvc.perform(put("/api/usuarises")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(usuarisDTO)))
                .andExpect(status().isOk());

        // Validate the Usuaris in the database
        List<Usuaris> usuarises = usuarisRepository.findAll();
        assertThat(usuarises).hasSize(databaseSizeBeforeUpdate);
        Usuaris testUsuaris = usuarises.get(usuarises.size() - 1);
        assertThat(testUsuaris.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testUsuaris.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsuaris.getDataInici()).isEqualTo(UPDATED_DATA_INICI);
        assertThat(testUsuaris.isActiu()).isEqualTo(UPDATED_ACTIU);
    }

    @Test
    @Transactional
    public void deleteUsuaris() throws Exception {
        // Initialize the database
        usuarisRepository.saveAndFlush(usuaris);
        int databaseSizeBeforeDelete = usuarisRepository.findAll().size();

        // Get the usuaris
        restUsuarisMockMvc.perform(delete("/api/usuarises/{id}", usuaris.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Usuaris> usuarises = usuarisRepository.findAll();
        assertThat(usuarises).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.br.donna.web.rest;

import com.br.donna.DiscoveryApp;

import com.br.donna.domain.Lawyer;
import com.br.donna.domain.LegalProcess;
import com.br.donna.repository.LawyerRepository;
import com.br.donna.service.LawyerService;
import com.br.donna.service.dto.LawyerDTO;
import com.br.donna.service.mapper.LawyerMapper;
import com.br.donna.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.br.donna.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LawyerResource REST controller.
 *
 * @see LawyerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryApp.class)
public class LawyerResourceIntTest {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private LawyerMapper lawyerMapper;

    @Autowired
    private LawyerService lawyerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLawyerMockMvc;

    private Lawyer lawyer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LawyerResource lawyerResource = new LawyerResource(lawyerService);
        this.restLawyerMockMvc = MockMvcBuilders.standaloneSetup(lawyerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lawyer createEntity(EntityManager em) {
        Lawyer lawyer = new Lawyer()
            .fullname(DEFAULT_FULLNAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        // Add required entity
        LegalProcess legalProcess = LegalProcessResourceIntTest.createEntity(em);
        em.persist(legalProcess);
        em.flush();
        lawyer.getLegalProcesses().add(legalProcess);
        return lawyer;
    }

    @Before
    public void initTest() {
        lawyer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLawyer() throws Exception {
        int databaseSizeBeforeCreate = lawyerRepository.findAll().size();

        // Create the Lawyer
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);
        restLawyerMockMvc.perform(post("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeCreate + 1);
        Lawyer testLawyer = lawyerList.get(lawyerList.size() - 1);
        assertThat(testLawyer.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testLawyer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLawyer.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createLawyerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lawyerRepository.findAll().size();

        // Create the Lawyer with an existing ID
        lawyer.setId(1L);
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLawyerMockMvc.perform(post("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFullnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lawyerRepository.findAll().size();
        // set the field null
        lawyer.setFullname(null);

        // Create the Lawyer, which fails.
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);

        restLawyerMockMvc.perform(post("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isBadRequest());

        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = lawyerRepository.findAll().size();
        // set the field null
        lawyer.setEmail(null);

        // Create the Lawyer, which fails.
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);

        restLawyerMockMvc.perform(post("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isBadRequest());

        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLawyers() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);

        // Get all the lawyerList
        restLawyerMockMvc.perform(get("/api/lawyers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lawyer.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getLawyer() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);

        // Get the lawyer
        restLawyerMockMvc.perform(get("/api/lawyers/{id}", lawyer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lawyer.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLawyer() throws Exception {
        // Get the lawyer
        restLawyerMockMvc.perform(get("/api/lawyers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLawyer() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);
        int databaseSizeBeforeUpdate = lawyerRepository.findAll().size();

        // Update the lawyer
        Lawyer updatedLawyer = lawyerRepository.findOne(lawyer.getId());
        updatedLawyer
            .fullname(UPDATED_FULLNAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        LawyerDTO lawyerDTO = lawyerMapper.toDto(updatedLawyer);

        restLawyerMockMvc.perform(put("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isOk());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeUpdate);
        Lawyer testLawyer = lawyerList.get(lawyerList.size() - 1);
        assertThat(testLawyer.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testLawyer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLawyer.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingLawyer() throws Exception {
        int databaseSizeBeforeUpdate = lawyerRepository.findAll().size();

        // Create the Lawyer
        LawyerDTO lawyerDTO = lawyerMapper.toDto(lawyer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLawyerMockMvc.perform(put("/api/lawyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lawyerDTO)))
            .andExpect(status().isCreated());

        // Validate the Lawyer in the database
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLawyer() throws Exception {
        // Initialize the database
        lawyerRepository.saveAndFlush(lawyer);
        int databaseSizeBeforeDelete = lawyerRepository.findAll().size();

        // Get the lawyer
        restLawyerMockMvc.perform(delete("/api/lawyers/{id}", lawyer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lawyer> lawyerList = lawyerRepository.findAll();
        assertThat(lawyerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lawyer.class);
        Lawyer lawyer1 = new Lawyer();
        lawyer1.setId(1L);
        Lawyer lawyer2 = new Lawyer();
        lawyer2.setId(lawyer1.getId());
        assertThat(lawyer1).isEqualTo(lawyer2);
        lawyer2.setId(2L);
        assertThat(lawyer1).isNotEqualTo(lawyer2);
        lawyer1.setId(null);
        assertThat(lawyer1).isNotEqualTo(lawyer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LawyerDTO.class);
        LawyerDTO lawyerDTO1 = new LawyerDTO();
        lawyerDTO1.setId(1L);
        LawyerDTO lawyerDTO2 = new LawyerDTO();
        assertThat(lawyerDTO1).isNotEqualTo(lawyerDTO2);
        lawyerDTO2.setId(lawyerDTO1.getId());
        assertThat(lawyerDTO1).isEqualTo(lawyerDTO2);
        lawyerDTO2.setId(2L);
        assertThat(lawyerDTO1).isNotEqualTo(lawyerDTO2);
        lawyerDTO1.setId(null);
        assertThat(lawyerDTO1).isNotEqualTo(lawyerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lawyerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lawyerMapper.fromId(null)).isNull();
    }
}

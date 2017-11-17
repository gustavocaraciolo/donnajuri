package com.br.donna.web.rest;

import com.br.donna.DiscoveryApp;

import com.br.donna.domain.Authority;
import com.br.donna.domain.LegalProcess;
import com.br.donna.domain.User;
import com.br.donna.repository.AuthorityRepository;
import com.br.donna.repository.LegalProcessRepository;
import com.br.donna.security.AuthoritiesConstants;
import com.br.donna.service.LegalProcessService;
import com.br.donna.service.dto.LegalProcessDTO;
import com.br.donna.service.mapper.LegalProcessMapper;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.br.donna.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.br.donna.domain.enumeration.Status;
/**
 * Test class for the LegalProcessResource REST controller.
 *
 * @see LegalProcessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryApp.class)
public class LegalProcessResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.PENDENTE;
    private static final Status UPDATED_STATUS = Status.PERDIDO;

    private static final String DEFAULT_ADVERSYPART = "AAAAAAAAAA";
    private static final String UPDATED_ADVERSYPART = "BBBBBBBBBB";

    @Autowired
    private LegalProcessRepository legalProcessRepository;

    @Autowired
    private LegalProcessMapper legalProcessMapper;

    @Autowired
    private LegalProcessService legalProcessService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private static AuthorityRepository authorityRepository;

    @Autowired
    private EntityManager em;

    private MockMvc restLegalProcessMockMvc;

    private LegalProcess legalProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LegalProcessResource legalProcessResource = new LegalProcessResource(legalProcessService);
        this.restLegalProcessMockMvc = MockMvcBuilders.standaloneSetup(legalProcessResource)
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
    public static LegalProcess createEntity(EntityManager em) {
        LegalProcess legalProcess = new LegalProcess()
            .number(DEFAULT_NUMBER)
            .status(DEFAULT_STATUS)
            .adversypart(DEFAULT_ADVERSYPART);
        // Add required entity

        User lawyer = UserResourceIntTest.createEntity(em);
        Set<Authority> authorities = new HashSet<>(1);
        authorities.add(authorityRepository.findOne(AuthoritiesConstants.ADVOGADO));
        lawyer.setAuthorities(authorities);
        em.persist(lawyer);
        em.flush();
        legalProcess.getUser().add(lawyer);
        return legalProcess;
    }

    @Before
    public void initTest() {
        legalProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createLegalProcess() throws Exception {
        int databaseSizeBeforeCreate = legalProcessRepository.findAll().size();

        // Create the LegalProcess
        LegalProcessDTO legalProcessDTO = legalProcessMapper.toDto(legalProcess);
        restLegalProcessMockMvc.perform(post("/api/legal-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legalProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the LegalProcess in the database
        List<LegalProcess> legalProcessList = legalProcessRepository.findAll();
        assertThat(legalProcessList).hasSize(databaseSizeBeforeCreate + 1);
        LegalProcess testLegalProcess = legalProcessList.get(legalProcessList.size() - 1);
        assertThat(testLegalProcess.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testLegalProcess.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLegalProcess.getAdversypart()).isEqualTo(DEFAULT_ADVERSYPART);
    }

    @Test
    @Transactional
    public void createLegalProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = legalProcessRepository.findAll().size();

        // Create the LegalProcess with an existing ID
        legalProcess.setId(1L);
        LegalProcessDTO legalProcessDTO = legalProcessMapper.toDto(legalProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegalProcessMockMvc.perform(post("/api/legal-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legalProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LegalProcess in the database
        List<LegalProcess> legalProcessList = legalProcessRepository.findAll();
        assertThat(legalProcessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = legalProcessRepository.findAll().size();
        // set the field null
        legalProcess.setNumber(null);

        // Create the LegalProcess, which fails.
        LegalProcessDTO legalProcessDTO = legalProcessMapper.toDto(legalProcess);

        restLegalProcessMockMvc.perform(post("/api/legal-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legalProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LegalProcess> legalProcessList = legalProcessRepository.findAll();
        assertThat(legalProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLegalProcesses() throws Exception {
        // Initialize the database
        legalProcessRepository.saveAndFlush(legalProcess);

        // Get all the legalProcessList
        restLegalProcessMockMvc.perform(get("/api/legal-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legalProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].adversypart").value(hasItem(DEFAULT_ADVERSYPART.toString())));
    }

    @Test
    @Transactional
    public void getLegalProcess() throws Exception {
        // Initialize the database
        legalProcessRepository.saveAndFlush(legalProcess);

        // Get the legalProcess
        restLegalProcessMockMvc.perform(get("/api/legal-processes/{id}", legalProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(legalProcess.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.adversypart").value(DEFAULT_ADVERSYPART.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLegalProcess() throws Exception {
        // Get the legalProcess
        restLegalProcessMockMvc.perform(get("/api/legal-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegalProcess() throws Exception {
        // Initialize the database
        legalProcessRepository.saveAndFlush(legalProcess);
        int databaseSizeBeforeUpdate = legalProcessRepository.findAll().size();

        // Update the legalProcess
        LegalProcess updatedLegalProcess = legalProcessRepository.findOne(legalProcess.getId());
        updatedLegalProcess
            .number(UPDATED_NUMBER)
            .status(UPDATED_STATUS)
            .adversypart(UPDATED_ADVERSYPART);
        LegalProcessDTO legalProcessDTO = legalProcessMapper.toDto(updatedLegalProcess);

        restLegalProcessMockMvc.perform(put("/api/legal-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legalProcessDTO)))
            .andExpect(status().isOk());

        // Validate the LegalProcess in the database
        List<LegalProcess> legalProcessList = legalProcessRepository.findAll();
        assertThat(legalProcessList).hasSize(databaseSizeBeforeUpdate);
        LegalProcess testLegalProcess = legalProcessList.get(legalProcessList.size() - 1);
        assertThat(testLegalProcess.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testLegalProcess.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLegalProcess.getAdversypart()).isEqualTo(UPDATED_ADVERSYPART);
    }

    @Test
    @Transactional
    public void updateNonExistingLegalProcess() throws Exception {
        int databaseSizeBeforeUpdate = legalProcessRepository.findAll().size();

        // Create the LegalProcess
        LegalProcessDTO legalProcessDTO = legalProcessMapper.toDto(legalProcess);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLegalProcessMockMvc.perform(put("/api/legal-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legalProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the LegalProcess in the database
        List<LegalProcess> legalProcessList = legalProcessRepository.findAll();
        assertThat(legalProcessList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLegalProcess() throws Exception {
        // Initialize the database
        legalProcessRepository.saveAndFlush(legalProcess);
        int databaseSizeBeforeDelete = legalProcessRepository.findAll().size();

        // Get the legalProcess
        restLegalProcessMockMvc.perform(delete("/api/legal-processes/{id}", legalProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LegalProcess> legalProcessList = legalProcessRepository.findAll();
        assertThat(legalProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LegalProcess.class);
        LegalProcess legalProcess1 = new LegalProcess();
        legalProcess1.setId(1L);
        LegalProcess legalProcess2 = new LegalProcess();
        legalProcess2.setId(legalProcess1.getId());
        assertThat(legalProcess1).isEqualTo(legalProcess2);
        legalProcess2.setId(2L);
        assertThat(legalProcess1).isNotEqualTo(legalProcess2);
        legalProcess1.setId(null);
        assertThat(legalProcess1).isNotEqualTo(legalProcess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LegalProcessDTO.class);
        LegalProcessDTO legalProcessDTO1 = new LegalProcessDTO();
        legalProcessDTO1.setId(1L);
        LegalProcessDTO legalProcessDTO2 = new LegalProcessDTO();
        assertThat(legalProcessDTO1).isNotEqualTo(legalProcessDTO2);
        legalProcessDTO2.setId(legalProcessDTO1.getId());
        assertThat(legalProcessDTO1).isEqualTo(legalProcessDTO2);
        legalProcessDTO2.setId(2L);
        assertThat(legalProcessDTO1).isNotEqualTo(legalProcessDTO2);
        legalProcessDTO1.setId(null);
        assertThat(legalProcessDTO1).isNotEqualTo(legalProcessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(legalProcessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(legalProcessMapper.fromId(null)).isNull();
    }
}

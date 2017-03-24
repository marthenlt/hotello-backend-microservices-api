package com.hotello.housekeeping.schedule.api.web.rest;

import com.hotello.housekeeping.schedule.api.BackendapiApp;

import com.hotello.housekeeping.schedule.api.domain.Cleaner;
import com.hotello.housekeeping.schedule.api.repository.CleanerRepository;
import com.hotello.housekeeping.schedule.api.service.CleanerService;
import com.hotello.housekeeping.schedule.api.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CleanerResource REST controller.
 *
 * @see CleanerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendapiApp.class)
public class CleanerResourceIntTest {

    private static final String DEFAULT_CLEANER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLEANER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLOOR_PREFERANCE = 1;
    private static final Integer UPDATED_FLOOR_PREFERANCE = 2;

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    @Autowired
    private CleanerRepository cleanerRepository;

    @Autowired
    private CleanerService cleanerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCleanerMockMvc;

    private Cleaner cleaner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CleanerResource cleanerResource = new CleanerResource(cleanerService);
        this.restCleanerMockMvc = MockMvcBuilders.standaloneSetup(cleanerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cleaner createEntity() {
        Cleaner cleaner = new Cleaner()
            .cleanerName(DEFAULT_CLEANER_NAME)
            .floorPreferance(DEFAULT_FLOOR_PREFERANCE)
            .phoneNo(DEFAULT_PHONE_NO);
        return cleaner;
    }

    @Before
    public void initTest() {
        cleanerRepository.deleteAll();
        cleaner = createEntity();
    }

    @Test
    public void createCleaner() throws Exception {
        int databaseSizeBeforeCreate = cleanerRepository.findAll().size();

        // Create the Cleaner
        restCleanerMockMvc.perform(post("/api/cleaners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cleaner)))
            .andExpect(status().isCreated());

        // Validate the Cleaner in the database
        List<Cleaner> cleanerList = cleanerRepository.findAll();
        assertThat(cleanerList).hasSize(databaseSizeBeforeCreate + 1);
        Cleaner testCleaner = cleanerList.get(cleanerList.size() - 1);
        assertThat(testCleaner.getCleanerName()).isEqualTo(DEFAULT_CLEANER_NAME);
        assertThat(testCleaner.getFloorPreferance()).isEqualTo(DEFAULT_FLOOR_PREFERANCE);
        assertThat(testCleaner.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
    }

    @Test
    public void createCleanerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cleanerRepository.findAll().size();

        // Create the Cleaner with an existing ID
        cleaner.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCleanerMockMvc.perform(post("/api/cleaners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cleaner)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Cleaner> cleanerList = cleanerRepository.findAll();
        assertThat(cleanerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCleaners() throws Exception {
        // Initialize the database
        cleanerRepository.save(cleaner);

        // Get all the cleanerList
        restCleanerMockMvc.perform(get("/api/cleaners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cleaner.getId())))
            .andExpect(jsonPath("$.[*].cleanerName").value(hasItem(DEFAULT_CLEANER_NAME.toString())))
            .andExpect(jsonPath("$.[*].floorPreferance").value(hasItem(DEFAULT_FLOOR_PREFERANCE)))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO.toString())));
    }

    @Test
    public void getCleaner() throws Exception {
        // Initialize the database
        cleanerRepository.save(cleaner);

        // Get the cleaner
        restCleanerMockMvc.perform(get("/api/cleaners/{id}", cleaner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cleaner.getId()))
            .andExpect(jsonPath("$.cleanerName").value(DEFAULT_CLEANER_NAME.toString()))
            .andExpect(jsonPath("$.floorPreferance").value(DEFAULT_FLOOR_PREFERANCE))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO.toString()));
    }

    @Test
    public void getNonExistingCleaner() throws Exception {
        // Get the cleaner
        restCleanerMockMvc.perform(get("/api/cleaners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCleaner() throws Exception {
        // Initialize the database
        cleanerService.save(cleaner);

        int databaseSizeBeforeUpdate = cleanerRepository.findAll().size();

        // Update the cleaner
        Cleaner updatedCleaner = cleanerRepository.findOne(cleaner.getId());
        updatedCleaner
            .cleanerName(UPDATED_CLEANER_NAME)
            .floorPreferance(UPDATED_FLOOR_PREFERANCE)
            .phoneNo(UPDATED_PHONE_NO);

        restCleanerMockMvc.perform(put("/api/cleaners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCleaner)))
            .andExpect(status().isOk());

        // Validate the Cleaner in the database
        List<Cleaner> cleanerList = cleanerRepository.findAll();
        assertThat(cleanerList).hasSize(databaseSizeBeforeUpdate);
        Cleaner testCleaner = cleanerList.get(cleanerList.size() - 1);
        assertThat(testCleaner.getCleanerName()).isEqualTo(UPDATED_CLEANER_NAME);
        assertThat(testCleaner.getFloorPreferance()).isEqualTo(UPDATED_FLOOR_PREFERANCE);
        assertThat(testCleaner.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
    }

    @Test
    public void updateNonExistingCleaner() throws Exception {
        int databaseSizeBeforeUpdate = cleanerRepository.findAll().size();

        // Create the Cleaner

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCleanerMockMvc.perform(put("/api/cleaners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cleaner)))
            .andExpect(status().isCreated());

        // Validate the Cleaner in the database
        List<Cleaner> cleanerList = cleanerRepository.findAll();
        assertThat(cleanerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteCleaner() throws Exception {
        // Initialize the database
        cleanerService.save(cleaner);

        int databaseSizeBeforeDelete = cleanerRepository.findAll().size();

        // Get the cleaner
        restCleanerMockMvc.perform(delete("/api/cleaners/{id}", cleaner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cleaner> cleanerList = cleanerRepository.findAll();
        assertThat(cleanerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cleaner.class);
    }
}

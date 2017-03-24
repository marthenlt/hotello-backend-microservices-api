package com.hotello.housekeeping.schedule.api.web.rest;

import com.hotello.housekeeping.schedule.api.BackendapiApp;

import com.hotello.housekeeping.schedule.api.domain.WorkSchedule;
import com.hotello.housekeeping.schedule.api.repository.WorkScheduleRepository;
import com.hotello.housekeeping.schedule.api.service.WorkScheduleService;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkScheduleResource REST controller.
 *
 * @see WorkScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendapiApp.class)
public class WorkScheduleResourceIntTest {

    private static final LocalDate DEFAULT_SCHEDULE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ROOM_NO = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CLEANER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLEANER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restWorkScheduleMockMvc;

    private WorkSchedule workSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkScheduleResource workScheduleResource = new WorkScheduleResource(workScheduleService);
        this.restWorkScheduleMockMvc = MockMvcBuilders.standaloneSetup(workScheduleResource)
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
    public static WorkSchedule createEntity() {
        WorkSchedule workSchedule = new WorkSchedule()
            .scheduleDate(DEFAULT_SCHEDULE_DATE)
            .roomNo(DEFAULT_ROOM_NO)
            .cleanerName(DEFAULT_CLEANER_NAME)
            .desc(DEFAULT_DESC);
        return workSchedule;
    }

    @Before
    public void initTest() {
        workScheduleRepository.deleteAll();
        workSchedule = createEntity();
    }

    @Test
    public void createWorkSchedule() throws Exception {
        int databaseSizeBeforeCreate = workScheduleRepository.findAll().size();

        // Create the WorkSchedule
        restWorkScheduleMockMvc.perform(post("/api/work-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workSchedule)))
            .andExpect(status().isCreated());

        // Validate the WorkSchedule in the database
        List<WorkSchedule> workScheduleList = workScheduleRepository.findAll();
        assertThat(workScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        WorkSchedule testWorkSchedule = workScheduleList.get(workScheduleList.size() - 1);
        assertThat(testWorkSchedule.getScheduleDate()).isEqualTo(DEFAULT_SCHEDULE_DATE);
        assertThat(testWorkSchedule.getRoomNo()).isEqualTo(DEFAULT_ROOM_NO);
        assertThat(testWorkSchedule.getCleanerName()).isEqualTo(DEFAULT_CLEANER_NAME);
        assertThat(testWorkSchedule.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    public void createWorkScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workScheduleRepository.findAll().size();

        // Create the WorkSchedule with an existing ID
        workSchedule.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkScheduleMockMvc.perform(post("/api/work-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkSchedule> workScheduleList = workScheduleRepository.findAll();
        assertThat(workScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllWorkSchedules() throws Exception {
        // Initialize the database
        workScheduleRepository.save(workSchedule);

        // Get all the workScheduleList
        restWorkScheduleMockMvc.perform(get("/api/work-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workSchedule.getId())))
            .andExpect(jsonPath("$.[*].scheduleDate").value(hasItem(DEFAULT_SCHEDULE_DATE.toString())))
            .andExpect(jsonPath("$.[*].roomNo").value(hasItem(DEFAULT_ROOM_NO.toString())))
            .andExpect(jsonPath("$.[*].cleanerName").value(hasItem(DEFAULT_CLEANER_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }

    @Test
    public void getWorkSchedule() throws Exception {
        // Initialize the database
        workScheduleRepository.save(workSchedule);

        // Get the workSchedule
        restWorkScheduleMockMvc.perform(get("/api/work-schedules/{id}", workSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workSchedule.getId()))
            .andExpect(jsonPath("$.scheduleDate").value(DEFAULT_SCHEDULE_DATE.toString()))
            .andExpect(jsonPath("$.roomNo").value(DEFAULT_ROOM_NO.toString()))
            .andExpect(jsonPath("$.cleanerName").value(DEFAULT_CLEANER_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    public void getNonExistingWorkSchedule() throws Exception {
        // Get the workSchedule
        restWorkScheduleMockMvc.perform(get("/api/work-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWorkSchedule() throws Exception {
        // Initialize the database
        workScheduleService.save(workSchedule);

        int databaseSizeBeforeUpdate = workScheduleRepository.findAll().size();

        // Update the workSchedule
        WorkSchedule updatedWorkSchedule = workScheduleRepository.findOne(workSchedule.getId());
        updatedWorkSchedule
            .scheduleDate(UPDATED_SCHEDULE_DATE)
            .roomNo(UPDATED_ROOM_NO)
            .cleanerName(UPDATED_CLEANER_NAME)
            .desc(UPDATED_DESC);

        restWorkScheduleMockMvc.perform(put("/api/work-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkSchedule)))
            .andExpect(status().isOk());

        // Validate the WorkSchedule in the database
        List<WorkSchedule> workScheduleList = workScheduleRepository.findAll();
        assertThat(workScheduleList).hasSize(databaseSizeBeforeUpdate);
        WorkSchedule testWorkSchedule = workScheduleList.get(workScheduleList.size() - 1);
        assertThat(testWorkSchedule.getScheduleDate()).isEqualTo(UPDATED_SCHEDULE_DATE);
        assertThat(testWorkSchedule.getRoomNo()).isEqualTo(UPDATED_ROOM_NO);
        assertThat(testWorkSchedule.getCleanerName()).isEqualTo(UPDATED_CLEANER_NAME);
        assertThat(testWorkSchedule.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    public void updateNonExistingWorkSchedule() throws Exception {
        int databaseSizeBeforeUpdate = workScheduleRepository.findAll().size();

        // Create the WorkSchedule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkScheduleMockMvc.perform(put("/api/work-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workSchedule)))
            .andExpect(status().isCreated());

        // Validate the WorkSchedule in the database
        List<WorkSchedule> workScheduleList = workScheduleRepository.findAll();
        assertThat(workScheduleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteWorkSchedule() throws Exception {
        // Initialize the database
        workScheduleService.save(workSchedule);

        int databaseSizeBeforeDelete = workScheduleRepository.findAll().size();

        // Get the workSchedule
        restWorkScheduleMockMvc.perform(delete("/api/work-schedules/{id}", workSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkSchedule> workScheduleList = workScheduleRepository.findAll();
        assertThat(workScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkSchedule.class);
    }
}

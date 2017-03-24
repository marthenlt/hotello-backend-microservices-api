package com.hotello.housekeeping.schedule.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotello.housekeeping.schedule.api.domain.WorkSchedule;
import com.hotello.housekeeping.schedule.api.service.WorkScheduleService;
import com.hotello.housekeeping.schedule.api.web.rest.util.HeaderUtil;
import com.hotello.housekeeping.schedule.api.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WorkSchedule.
 */
@RestController
@RequestMapping("/api")
public class WorkScheduleResource {

    private final Logger log = LoggerFactory.getLogger(WorkScheduleResource.class);

    private static final String ENTITY_NAME = "workSchedule";
        
    private final WorkScheduleService workScheduleService;

    public WorkScheduleResource(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    /**
     * POST  /work-schedules : Create a new workSchedule.
     *
     * @param workSchedule the workSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workSchedule, or with status 400 (Bad Request) if the workSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-schedules")
    @Timed
    public ResponseEntity<WorkSchedule> createWorkSchedule(@RequestBody WorkSchedule workSchedule) throws URISyntaxException {
        log.debug("REST request to save WorkSchedule : {}", workSchedule);
        if (workSchedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workSchedule cannot already have an ID")).body(null);
        }
        WorkSchedule result = workScheduleService.save(workSchedule);
        return ResponseEntity.created(new URI("/api/work-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-schedules : Updates an existing workSchedule.
     *
     * @param workSchedule the workSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workSchedule,
     * or with status 400 (Bad Request) if the workSchedule is not valid,
     * or with status 500 (Internal Server Error) if the workSchedule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-schedules")
    @Timed
    public ResponseEntity<WorkSchedule> updateWorkSchedule(@RequestBody WorkSchedule workSchedule) throws URISyntaxException {
        log.debug("REST request to update WorkSchedule : {}", workSchedule);
        if (workSchedule.getId() == null) {
            return createWorkSchedule(workSchedule);
        }
        WorkSchedule result = workScheduleService.save(workSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-schedules : get all the workSchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workSchedules in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/work-schedules")
    @Timed
    public ResponseEntity<List<WorkSchedule>> getAllWorkSchedules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WorkSchedules");
        Page<WorkSchedule> page = workScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /work-schedules/:id : get the "id" workSchedule.
     *
     * @param id the id of the workSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workSchedule, or with status 404 (Not Found)
     */
    @GetMapping("/work-schedules/{id}")
    @Timed
    public ResponseEntity<WorkSchedule> getWorkSchedule(@PathVariable String id) {
        log.debug("REST request to get WorkSchedule : {}", id);
        WorkSchedule workSchedule = workScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workSchedule));
    }

    /**
     * DELETE  /work-schedules/:id : delete the "id" workSchedule.
     *
     * @param id the id of the workSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkSchedule(@PathVariable String id) {
        log.debug("REST request to delete WorkSchedule : {}", id);
        workScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

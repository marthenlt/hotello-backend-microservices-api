package com.hotello.housekeeping.schedule.api.repository;

import com.hotello.housekeeping.schedule.api.domain.WorkSchedule;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the WorkSchedule entity.
 */
@SuppressWarnings("unused")
public interface WorkScheduleRepository extends MongoRepository<WorkSchedule,String> {

}

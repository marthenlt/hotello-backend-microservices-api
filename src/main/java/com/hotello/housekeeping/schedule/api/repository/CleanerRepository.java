package com.hotello.housekeeping.schedule.api.repository;

import com.hotello.housekeeping.schedule.api.domain.Cleaner;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Cleaner entity.
 */
@SuppressWarnings("unused")
public interface CleanerRepository extends MongoRepository<Cleaner,String> {

}

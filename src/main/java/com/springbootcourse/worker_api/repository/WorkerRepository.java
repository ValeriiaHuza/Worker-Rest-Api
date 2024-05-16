package com.springbootcourse.worker_api.repository;

import com.springbootcourse.worker_api.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkerRepository extends JpaRepository<Worker, Integer>, JpaSpecificationExecutor<Worker> {


}
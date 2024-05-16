package com.springbootcourse.worker_api.service;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.model.Worker;

import java.time.LocalDate;
import java.util.List;

public interface WorkerService {

    Worker create(Worker worker);
    Worker getWorkerById(Integer id);
    Worker updateWorkerById(WorkerDTO worker, Integer id);
    void deleteWorkerById(Integer id);
    List<Worker> getWorkersList(String firstName, String lastName, LocalDate birthday, String email, String position);
}

package com.springbootcourse.worker_api.service;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.dto.WorkerDTOResponse;
import com.springbootcourse.worker_api.model.Worker;

import java.util.List;

public interface WorkerService {

    Worker create(Worker worker);
    Worker getWorkerById(Integer id);
    List<Worker> getWorkersList();
    Worker updateWorkerById(WorkerDTO worker, Integer id);
    void deleteWorkerById(Integer id);

}

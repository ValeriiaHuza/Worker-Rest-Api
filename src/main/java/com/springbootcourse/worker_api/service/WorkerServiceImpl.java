package com.springbootcourse.worker_api.service;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.mapper.WorkerMapper;
import com.springbootcourse.worker_api.model.Worker;
import com.springbootcourse.worker_api.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService{

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public Worker create(WorkerDTO worker) {
        return workerRepository.save(WorkerMapper.dtoToWorker(worker));
    }
}

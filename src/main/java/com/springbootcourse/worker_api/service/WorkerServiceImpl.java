package com.springbootcourse.worker_api.service;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.dto.WorkerDTOResponse;
import com.springbootcourse.worker_api.error.WorkerNotFoundException;
import com.springbootcourse.worker_api.mapper.WorkerMapper;
import com.springbootcourse.worker_api.model.Worker;
import com.springbootcourse.worker_api.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl implements WorkerService{

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public Worker create(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public Worker getWorkerById(Integer id) {
        Optional<Worker> workerOptional = workerRepository.findById(id);

        Worker worker;

        if(workerOptional.isPresent()) {
            worker = workerOptional.get();
        }
        else {
            throw new WorkerNotFoundException("Worker with id: " + id + " not found");
        }

        return worker;
    }

    @Override
    public List<Worker> getWorkersList() {
        return workerRepository.findAll();
    }
}

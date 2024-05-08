package com.springbootcourse.worker_api.controller;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.dto.WorkerDTOResponse;
import com.springbootcourse.worker_api.mapper.WorkerMapper;
import com.springbootcourse.worker_api.model.Worker;
import com.springbootcourse.worker_api.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WorkerRestController {

    private final WorkerService workerService;

    @Autowired
    public WorkerRestController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/workers")
    public ResponseEntity<WorkerDTOResponse> createWorker(@Valid @RequestBody WorkerDTO workerDTO){
        Worker created = workerService.create(workerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(WorkerMapper.workerToDTOResponse(created));
    }
}

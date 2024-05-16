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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workers")
public class WorkerRestController {

    private final WorkerService workerService;

    @Autowired
    public WorkerRestController(WorkerService workerService) {
        this.workerService = workerService;
    }


    @GetMapping
    public ResponseEntity<List<WorkerDTOResponse>> getWorkersList(@RequestParam(required = false) String firstName,
                                                                  @RequestParam(required = false) String lastName,
                                                                  @RequestParam(required = false) LocalDate birthday,
                                                                  @RequestParam(required = false) String email,
                                                                  @RequestParam(required = false) String positionJob){
        List<Worker> workers = workerService.getWorkersList(firstName, lastName, birthday, email, positionJob);

        return ResponseEntity.ok(workers.stream().map(WorkerMapper::workerToDTOResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{workerId}")
    public ResponseEntity<WorkerDTOResponse> getWorkerById(@PathVariable int workerId){
        Worker worker = workerService.getWorkerById(workerId);
        return ResponseEntity.ok(WorkerMapper.workerToDTOResponse(worker));
    }

    @PostMapping
    public ResponseEntity<WorkerDTOResponse> createWorker(@Valid @RequestBody WorkerDTO workerDTO){
        Worker created = workerService.create(WorkerMapper.dtoToWorker(workerDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(WorkerMapper.workerToDTOResponse(created));
    }

    @PutMapping("/{workerId}")
    public ResponseEntity<WorkerDTOResponse> updateWorkerById(@RequestBody WorkerDTO workerDTO, @PathVariable int workerId){
        Worker update = workerService.updateWorkerById(workerDTO, workerId);
        return ResponseEntity.ok(WorkerMapper.workerToDTOResponse(update));
    }

    @DeleteMapping("/{workerId}")
    public ResponseEntity<Void> deleteWorkerById(@PathVariable int workerId){
        workerService.deleteWorkerById(workerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

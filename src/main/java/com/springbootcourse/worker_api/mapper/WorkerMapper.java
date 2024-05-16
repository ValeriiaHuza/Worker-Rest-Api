package com.springbootcourse.worker_api.mapper;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.dto.WorkerDTOResponse;
import com.springbootcourse.worker_api.model.Worker;
import com.springbootcourse.worker_api.model.PhoneNumber;

import java.util.stream.Collectors;

public class WorkerMapper {

    public static WorkerDTO workerToDTO(Worker worker){
        return  WorkerDTO.builder()
                .firstName(worker.getFirstName())
                .lastName(worker.getLastName())
                .birthday(worker.getBirthday())
                .email(worker.getEmail())
                .positionJob(worker.getPositionJob())
                .phoneNumbers(worker.getPhoneNumbers().stream()
                        .map(PhoneNumber::getPhoneNumber)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static WorkerDTOResponse workerToDTOResponse(Worker worker){
        return WorkerDTOResponse.builder()
                .id(worker.getId())
                .firstName(worker.getFirstName())
                .lastName(worker.getLastName())
                .birthday(worker.getBirthday())
                .email(worker.getEmail())
                .positionJob(worker.getPositionJob())
                .phoneNumbers(worker.getPhoneNumbers().stream()
                        .map(PhoneNumber::getPhoneNumber)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Worker dtoToWorker(WorkerDTO workerDTO){
        Worker worker = new Worker();
        worker.setFirstName(workerDTO.getFirstName());
        worker.setLastName(workerDTO.getLastName());
        worker.setBirthday(workerDTO.getBirthday());
        worker.setEmail(workerDTO.getEmail());
        worker.setPositionJob(workerDTO.getPositionJob());
        if (workerDTO.getPhoneNumbers() != null) {
            worker.setPhoneNumbers(workerDTO.getPhoneNumbers().stream()
                    .map(phoneNumber -> {
                        PhoneNumber phoneEntity = new PhoneNumber();
                        phoneEntity.setPhoneNumber(phoneNumber);
                        phoneEntity.setWorker(worker);  // Ensure the back-reference to the worker is set
                        return phoneEntity;
                    })
                    .collect(Collectors.toSet()));
        }

        return worker;
    }
}

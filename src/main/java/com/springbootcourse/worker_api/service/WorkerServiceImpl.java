package com.springbootcourse.worker_api.service;

import com.springbootcourse.worker_api.dto.WorkerDTO;
import com.springbootcourse.worker_api.error.WorkerNotFoundException;
import com.springbootcourse.worker_api.model.PhoneNumber;
import com.springbootcourse.worker_api.model.Worker;
import com.springbootcourse.worker_api.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImpl implements WorkerService{
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{9,13}$");

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
    public List<Worker> getWorkersList(String firstName, String lastName, LocalDate birthday, String email, String position) {
        Specification<Worker> spec = Specification.where(null);

        if (firstName != null) {
            spec = spec.and(WorkerSpecifications.hasFirstName(firstName));
        }
        if (lastName != null) {
            spec = spec.and(WorkerSpecifications.hasLastName(lastName));
        }
        if (birthday != null) {
            spec = spec.and(WorkerSpecifications.hasBirthday(birthday));
        }
        if (email != null) {
            spec = spec.and(WorkerSpecifications.hasEmail(email));
        }
        if (position != null) {
            spec = spec.and(WorkerSpecifications.hasPosition(position));
        }

        return workerRepository.findAll(spec);
    }


    @Override
    public Worker updateWorkerById(WorkerDTO newWorker, Integer id) {
        
        Worker updateWorker = getWorkerById(id);
        
        validateWorker(updateWorker, newWorker);
        
        return workerRepository.save(updateWorker);
    }

    @Override
    public void deleteWorkerById(Integer id) {
        workerRepository.deleteById(id);
    }


    private void validateWorker(Worker updateWorker, WorkerDTO newWorker) {

        StringBuilder errorValidateMessage = new StringBuilder();

        if(newWorker.getFirstName()!=null){
            if (newWorker.getFirstName().isBlank()) {
                errorValidateMessage.append("firstName : First name can't be blank \n");
            } else {
                updateWorker.setFirstName(newWorker.getFirstName());
            }
        }

        if(newWorker.getLastName()!=null){
            if (newWorker.getLastName().isBlank()) {
                errorValidateMessage.append("lastName : Last name can't be blank \n");
            } else {
                updateWorker.setLastName(newWorker.getLastName());
            }
        }

        if(newWorker.getEmail()!=null){
            if (newWorker.getEmail().isBlank() || !newWorker.getEmail().matches("^[\\w-._]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                errorValidateMessage.append("email : Email format isn't correct \n");
            } else {
                updateWorker.setEmail(newWorker.getEmail());
            }
        }

        if (newWorker.getBirthday() != null) {
            LocalDate currentDate = LocalDate.now();

            if (newWorker.getBirthday().isAfter(currentDate)){
                errorValidateMessage.append("birthDate : Birthday date should be in the past! \n");
            }
            else {
                updateWorker.setBirthday(newWorker.getBirthday());
            }
        }

        if (newWorker.getPosition() != null) {
            updateWorker.setPosition(newWorker.getPosition());
        }

        if (newWorker.getPhoneNumbers() != null) {
            Set<String> invalidNumbers = newWorker.getPhoneNumbers().stream()
                    .filter(phone -> !PHONE_PATTERN.matcher(phone).matches())
                    .collect(Collectors.toSet());

            if(invalidNumbers.isEmpty()){
                updateWorker.setPhoneNumbers(newWorker.getPhoneNumbers().stream()
                        .map(phoneNumber -> {
                            PhoneNumber phoneEntity = new PhoneNumber();
                            phoneEntity.setPhoneNumber(phoneNumber);
                            phoneEntity.setWorker(updateWorker);  // Ensure the back-reference to the worker is set
                            return phoneEntity;
                        })
                        .collect(Collectors.toSet()));
            }
            else {
                errorValidateMessage.append("Invalid phone number(s): ").append(String.join(", ", invalidNumbers));
            }
        }

        if (!errorValidateMessage.isEmpty()) {
            throw new RuntimeException(errorValidateMessage.toString());
        }

    }
}

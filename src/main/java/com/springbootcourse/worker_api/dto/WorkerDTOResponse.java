package com.springbootcourse.worker_api.dto;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@SuperBuilder
@ToString
public class WorkerDTOResponse extends WorkerDTO{
    private Integer id;
    public WorkerDTOResponse(Integer id, String firstName, String lastName,LocalDate birthday, String email, String position, Set<String> phoneNumbers){
        super(firstName, lastName, birthday, email, position, phoneNumbers);
        this.id = id;
    }
}

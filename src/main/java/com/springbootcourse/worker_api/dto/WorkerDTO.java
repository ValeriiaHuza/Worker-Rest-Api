package com.springbootcourse.worker_api.dto;

import com.springbootcourse.worker_api.validator.ValidPhoneNumbers;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkerDTO {

    @NotBlank(message = "First name is required!")
    private String firstName;
    @NotBlank(message = "Last name is required!")
    private String lastName;
    @Past(message = "Birthday date should be in the past!")
    private LocalDate birthday;
    @Email(regexp = "^[\\w-._]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not valid!")
    private String email;
    private String positionJob;
    @ValidPhoneNumbers()
    private Set<String> phoneNumbers;

    public WorkerDTO(String firstName, String lastName, LocalDate birthday, String email, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.positionJob = position;
    }
}

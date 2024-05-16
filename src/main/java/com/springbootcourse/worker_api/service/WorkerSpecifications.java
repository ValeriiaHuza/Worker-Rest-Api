package com.springbootcourse.worker_api.service;

import com.springbootcourse.worker_api.model.Worker;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class WorkerSpecifications {

        public static Specification<Worker> hasFirstName(String firstName) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), firstName);
        }

        public static Specification<Worker> hasLastName(String lastName) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("lastName"), lastName);
        }

        public static Specification<Worker> hasBirthday(LocalDate birthday) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("birthday"), birthday);
        }

        public static Specification<Worker> hasEmail(String email) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
        }

        public static Specification<Worker> hasPosition(String position) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("\"position\""), position);
        }
}

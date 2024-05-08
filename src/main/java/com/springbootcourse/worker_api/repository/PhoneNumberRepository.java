package com.springbootcourse.worker_api.repository;

import com.springbootcourse.worker_api.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
}
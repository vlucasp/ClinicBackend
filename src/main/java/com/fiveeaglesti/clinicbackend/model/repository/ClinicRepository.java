package com.fiveeaglesti.clinicbackend.model.repository;

import com.fiveeaglesti.clinicbackend.model.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}

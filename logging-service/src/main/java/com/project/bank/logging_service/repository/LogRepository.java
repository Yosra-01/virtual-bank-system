package com.project.bank.logging_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.logging_service.entity.LogDumpEntity;

@Repository
public interface LogRepository extends JpaRepository<LogDumpEntity, Long> {

} 

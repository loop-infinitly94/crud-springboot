package com.crudapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudapplication.model.StudentDetails;

public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Long>{
    
}

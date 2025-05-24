package com.workflow.repository;

import com.workflow.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    
    List<Workflow> findByActiveTrue();
    
    List<Workflow> findByNameContainingIgnoreCase(String name);
}

package com.deep.ims.repository;

import com.deep.ims.entity.Incident;
import com.deep.ims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidentRepo extends JpaRepository<Incident,String> {

    Optional<Incident> findTopByOrderByIdDesc();
    Optional<Incident> findByIdAndCreatedBy(String id, User createdBy);
}

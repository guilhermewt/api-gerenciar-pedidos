package com.webserviceproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webserviceproject.entities.RoleModel;

@Repository
public interface RoleModelRepository extends JpaRepository<RoleModel, Long>{

}

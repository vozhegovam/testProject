package com.projects.myproject.repository;


import com.projects.myproject.model.RequestStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStorageRepository extends JpaRepository<RequestStorage, Long> {
}

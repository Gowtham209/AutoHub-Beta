package com.AutoHub.autohub_backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AutoHub.autohub_backend.Entities.CarModels;

@Repository
public interface CarModelsRepo extends JpaRepository<CarModels,Long> {
	@Query("SELECT m FROM CarModels m JOIN m.category c WHERE c.categoryId = :cid")
	List<CarModels> getCategoryCarModels(@Param("cid") Long id);
}

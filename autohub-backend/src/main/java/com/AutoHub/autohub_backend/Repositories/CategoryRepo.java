package com.AutoHub.autohub_backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByCarType(String carType);
    
    @Query("SELECT c FROM Category c") 
    List<Category> getAllCategories();
    
}

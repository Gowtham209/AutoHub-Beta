package com.AutoHub.autohub_backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AutoHub.autohub_backend.Entities.CarPurchase;


@Repository
public interface CarPurchaseRepo extends JpaRepository<CarPurchase,Long> {

	@Query(value="SELECT * FROM car_purchase cp WHERE cp.user_id = :userId",
			nativeQuery = true)
	List<CarPurchase> getAllCarPurchaseOfAUser(@Param("userId") Long userId);
}
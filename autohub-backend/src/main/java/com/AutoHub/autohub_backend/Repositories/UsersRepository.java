 package com.AutoHub.autohub_backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long>{

	@Query(value="SELECT c.* FROM car_models c JOIN favorite_cars f ON c.model_id = f.model_id WHERE f.user_id = :userId",
			nativeQuery = true)
	List<CarModels> fetchAllFavoriteCarsOfUser(@Param("userId") Long userId);

	Users findByEmailId(String emailId);

	 List<Users> findByIsVisibleTrue();
	
	boolean existsByEmailId(String emailId);

}

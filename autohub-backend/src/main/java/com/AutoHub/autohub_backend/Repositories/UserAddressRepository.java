package com.AutoHub.autohub_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.AutoHub.autohub_backend.Entities.UserAddresses;
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddresses,Long>{

//	@Modifying
//	@Query(value="DELETE FROM UserAddress ua WHERE ua.id = :id",
//	nativeQuery=true)
//	void deleteByIdCustom(@Param("id") Long id);
	@Modifying
	@Transactional
	@Query("DELETE FROM UserAddresses ua WHERE ua.id = :id")
	void deleteByIdCustom(@Param("id") Long id);
}
package com.AutoHub.autohub_backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.AutoHub.autohub_backend.Entities.TestDriveBooking;


@Repository
public interface TestDriveBookingRepo extends JpaRepository<TestDriveBooking,Long>{

	@Query(value="SELECT * FROM test_drive_booking tdb WHERE tdb.user_id = :userId",
			nativeQuery = true)
	List<TestDriveBooking> getAllBookingOfAUser(@Param("userId") Long userId);

}

package com.AutoHub.autohub_backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AutoHub.autohub_backend.CustomExceptions.TestDriveBookingException;
import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.TestDriveBooking;
import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.Repositories.CarModelsRepo;
import com.AutoHub.autohub_backend.Repositories.TestDriveBookingRepo;
import com.AutoHub.autohub_backend.Repositories.UsersRepository;

@Service
public class TestDriveBookingService {
	
	@Autowired
	private TestDriveBookingRepo testDriveBookRepoObj;

	@Autowired
	private UsersRepository userRepoObj;
	
	@Autowired
	private CarModelsRepo carModelRepoObj;

	public TestDriveBooking bookTestDrive(Long userId, Long modelId, TestDriveBooking testDriveBookObj) {
		Users userObj=userRepoObj.findById(userId).orElse(null);
		CarModels carModelObj=carModelRepoObj.findById(modelId).orElse(null);
		testDriveBookObj.setUser(userObj);
		testDriveBookObj.setModel(carModelObj);
		TestDriveBooking responTestDrvBkObj=testDriveBookRepoObj.save(testDriveBookObj);
		return responTestDrvBkObj;
	}

	public TestDriveBooking getTestDrvBooking(Long tstDrvBkID) throws TestDriveBookingException {
		TestDriveBooking respObj=testDriveBookRepoObj.findById(tstDrvBkID).orElse(null);
		if(respObj==null)
			throw new TestDriveBookingException("Error Booking Not Present in Server");
		return respObj;
	}

	public List<TestDriveBooking> getTestDrvBookingList(Long userId) throws TestDriveBookingException {
		
		List<TestDriveBooking> lstRespon=testDriveBookRepoObj.getAllBookingOfAUser(userId);
		if(lstRespon==null)
			throw new TestDriveBookingException("Error No List Of Booking");
		
		return lstRespon;
	}

	public void deleteTestDriveBookingSlot(Long tstDrvBkID) throws TestDriveBookingException {
		
		System.out.println("Test Drive Booking Cancel service");
		TestDriveBooking tstDrvBkdbObject=testDriveBookRepoObj.findById(tstDrvBkID).orElseThrow(()->new TestDriveBookingException("Error No TestDrive Booking Present"));
		tstDrvBkdbObject.setIsVisible(false);
		testDriveBookRepoObj.save(tstDrvBkdbObject);
		
	}
	
}

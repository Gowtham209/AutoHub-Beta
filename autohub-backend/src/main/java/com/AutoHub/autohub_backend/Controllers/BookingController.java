package com.AutoHub.autohub_backend.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AutoHub.autohub_backend.CustomExceptions.PurchaseException;
import com.AutoHub.autohub_backend.CustomExceptions.TestDriveBookingException;
import com.AutoHub.autohub_backend.DTO.CarPurchaseDTO;
import com.AutoHub.autohub_backend.DTO.TestDriveBookingDTO;
import com.AutoHub.autohub_backend.Entities.CarPurchase;
import com.AutoHub.autohub_backend.Entities.TestDriveBooking;
import com.AutoHub.autohub_backend.Services.PurchaseService;
import com.AutoHub.autohub_backend.Services.TestDriveBookingService;

@RestController
@RequestMapping("api/v1/autohub") 
@CrossOrigin(origins="http://localhost:5173")
public class BookingController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TestDriveBookingService testDriveBookServObj;
	
	@Autowired
	private PurchaseService purchaseServObj;
	
	// TEST DRIVE BOOKING
	
	/* 
	 API http://localhost:8080/api/v1/autohub/user/{userId}/model/{modelId}/test-drive-booking
	                      Used to Book a Slot for Test Driving a Specific Car Model type
	*/
	@PostMapping(value="/user/{userId}/model/{modelId}/test-drive-booking",consumes = "application/json")
	public ResponseEntity<Object> addNewTestDriveBooking(@PathVariable Long userId,@PathVariable Long modelId
			,@RequestBody TestDriveBookingDTO testDriveBookDtoObj)
	{
		System.out.println("Hitted for Booking Test Drive\nDate:"+testDriveBookDtoObj.getBookedDate()+"\nDriveCompleted:"+testDriveBookDtoObj.getDriveCompleted());
		TestDriveBooking testDriveBookObj=modelMapper.map(testDriveBookDtoObj,TestDriveBooking.class);
		TestDriveBooking responseTestDriveBkObj=testDriveBookServObj.bookTestDrive(userId,modelId,testDriveBookObj);
		TestDriveBookingDTO responseTestDrvBkDTO=modelMapper.map(responseTestDriveBkObj,TestDriveBookingDTO.class);
		responseTestDrvBkDTO.getUser().setAddressList(null);
		responseTestDrvBkDTO.getUser().setTestDriveBookings(null);
		responseTestDrvBkDTO.getUser().setCarPurchased(null);
		return new ResponseEntity<>(responseTestDrvBkDTO, HttpStatus.OK);
	}
	
	/*
	  API http://localhost:8080/api/v1/autohub/user/{userId}/test-drive-booking/{tstDrvBkID}
	  					Used to Get Detail Info like CarModel Name,Date about the 
	  				Specific Test Drive Slot Booked by a USER
	 */
	@GetMapping(value="/user/{userId}/test-drive-booking/{tstDrvBkID}")
	public ResponseEntity<Object> getTestDriveBooking(@PathVariable Long userId,@PathVariable Long tstDrvBkID)
	{
		
		TestDriveBooking testDrvBkObj;
		try {
			testDrvBkObj = testDriveBookServObj.getTestDrvBooking(tstDrvBkID);
		} catch (TestDriveBookingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		TestDriveBookingDTO responseTestDrvBkDTO=modelMapper.map(testDrvBkObj,TestDriveBookingDTO.class);
		responseTestDrvBkDTO.getUser().setAddressList(null);
		responseTestDrvBkDTO.getUser().setTestDriveBookings(null);
		responseTestDrvBkDTO.getUser().setCarPurchased(null);
		return new ResponseEntity<>(responseTestDrvBkDTO, HttpStatus.OK);
	}
	
	/*
	  API http://localhost:8080/api/v1/autohub/user/{userId}/test-drive-bookings
	  		Used to Get List of Test Drive Slots Booked by a Specific User
	*/	
	@GetMapping(value="/user/{userId}/test-drive-bookings")
	public ResponseEntity<Object> getTestDriveBookingList(@PathVariable Long userId)
	{
		List<TestDriveBooking> testDrvBkListObj;
		try {
			testDrvBkListObj = testDriveBookServObj.getTestDrvBookingList(userId);
		} catch (TestDriveBookingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<TestDriveBookingDTO> responseDTOLstObj=testDrvBkListObj.stream().map(obj->{
		TestDriveBookingDTO resp= modelMapper.map(obj,TestDriveBookingDTO.class);
		resp.getUser().setAddressList(null);
		resp.getUser().setTestDriveBookings(null);
		resp.getUser().setCarPurchased(null);
		return resp;
		}).toList();
	     
		return new ResponseEntity<>(responseDTOLstObj, HttpStatus.OK);
	}
	
	/*
	  API http://localhost:8080/api/v1/autohub/user/{userId}/test-drive-booking/{tstDrvBkID}
	  				Used to Cancel a Specific Test Drive Booking Slot of a Specific User
	  
	 */
	@DeleteMapping(value="/user/{userId}/test-drive-booking/{tstDrvBkID}")
	public ResponseEntity<Object> cancelTestDriveBookingSlot(@PathVariable Long userId,@PathVariable Long tstDrvBkID)
	{
		System.out.println("Test Drive Booking Cancel controller");
		try {
			testDriveBookServObj.deleteTestDriveBookingSlot(tstDrvBkID);
		} catch (TestDriveBookingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
	}
	
	
	// PURCHASE
	
	/* 
	 API http://localhost:8080/api/v1/autohub/user/{userId}/model/{modelId}/car-purchase-booking
	                      Used to Purchase a Specific CarMode by a Specific User
	*/
	@PostMapping(value="/user/{userId}/model/{modelId}/car-purchase-booking",consumes = "application/json")
	public ResponseEntity<Object> addCarPurchaseBooking(@PathVariable Long userId,@PathVariable Long modelId
			,@RequestBody CarPurchaseDTO carPurchBookDtoObj)
	{
		CarPurchase carPurchObj=modelMapper.map(carPurchBookDtoObj,CarPurchase.class);
		
		CarPurchase responseTestDriveBkObj=purchaseServObj.carPurchBooking(userId,modelId,carPurchObj);
		CarPurchaseDTO responseCarPurchBkDTO=modelMapper.map(responseTestDriveBkObj,CarPurchaseDTO.class);
		System.out.println("AddCarPurchase:\n"+responseCarPurchBkDTO);
		responseCarPurchBkDTO.getUser().setAddressList(null);
		responseCarPurchBkDTO.getUser().setTestDriveBookings(null);
		responseCarPurchBkDTO.getUser().setCarPurchased(null);
		return new ResponseEntity<>(responseCarPurchBkDTO, HttpStatus.OK);
	}
	
	
	
	/*
	  API http://localhost:8080/api/v1/autohub/user/{userId}/car-purchase-booking/{carPrchBkID}
	  					Used to Get Detail Info like CarModel Name,Date about the 
	  				Specific Car Purchase Booked by a USER
	 */
	@GetMapping(value="/user/{userId}/car-purchase-booking/{carPrchBkID}")
	public ResponseEntity<Object> getPurchaseBooking(@PathVariable Long userId,@PathVariable Long carPrchBkID)
	{	
		CarPurchase carPurchBkObj;
		try {
			carPurchBkObj = purchaseServObj.getCarPurchaseBooking(carPrchBkID);
		} catch (PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		CarPurchaseDTO responseCarPurchBkDTO=modelMapper.map(carPurchBkObj,CarPurchaseDTO.class);
		responseCarPurchBkDTO.getUser().setAddressList(null);
		responseCarPurchBkDTO.getUser().setTestDriveBookings(null);
		responseCarPurchBkDTO.getUser().setCarPurchased(null);
		return new ResponseEntity<>(responseCarPurchBkDTO, HttpStatus.OK);
	}
	
	/*
	  API http://localhost:8080/api/v1/autohub/user/{userId}/car-purchase-bookings
	  		Used to Get List of Car Purchased Booked by a Specific User
	*/
	@GetMapping(value="/user/{userId}/car-purchase-bookings")
	public ResponseEntity<Object> getPurchaseBookingList(@PathVariable Long userId)
	{
		
		List<CarPurchase> carPurchBkLstObj;
		try {
			carPurchBkLstObj = purchaseServObj.getCarPurchaseBookingList(userId);
		} catch (PurchaseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		List<CarPurchaseDTO> responseDTOLstObj=carPurchBkLstObj.stream().map(obj->{
			CarPurchaseDTO resp=modelMapper.map(obj,CarPurchaseDTO.class);
			resp.getUser().setAddressList(null);
			resp.getUser().setTestDriveBookings(null);
			resp.getUser().setCarPurchased(null);
			return resp;
			}).toList();
		return new ResponseEntity<>(responseDTOLstObj, HttpStatus.OK);
	}
	
	/*
	  API http://localhost:8080/api/v1/autohub/user/{userId}/car-purchase-booking/{carPrchBkID}
	  				Used to Cancel a Specific Car Purchase Booking Slot of a Specific User
	  
	 */
	 @DeleteMapping(value = "/user/{userId}/car-purchase-booking/{carPrchBkID}")
	    public ResponseEntity<Object> deletePurchaseBooking(@PathVariable Long userId, @PathVariable Long carPrchBkID) {
	        LocalDateTime currentTime = LocalDateTime.now();  // Use LocalDateTime instead of System.currentTimeMillis()

	        try {
	            purchaseServObj.deleteCarPurchaseBooking(carPrchBkID, currentTime);
	        } catch (PurchaseException e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
	    }
//	@DeleteMapping(value="/user/{userId}/car-purchase-booking/{carPrchBkID}")
//	public ResponseEntity<Object> deletePurchaseBooking(@PathVariable Long userId,@PathVariable Long carPrchBkID)
//	{
//		Long currentTime=System.currentTimeMillis();
//		try {
//			purchaseServObj.deleteCarPurchaseBooking(carPrchBkID,currentTime);
//		} catch (PurchaseException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//		
//		return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
//	}
	
}

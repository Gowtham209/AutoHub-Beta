package com.AutoHub.autohub_backend.Services;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AutoHub.autohub_backend.CustomExceptions.PurchaseException;
import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.CarPurchase;
import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.Repositories.CarModelsRepo;
import com.AutoHub.autohub_backend.Repositories.CarPurchaseRepo;
import com.AutoHub.autohub_backend.Repositories.UsersRepository;


@Service
public class PurchaseService {

	@Autowired
	private CarPurchaseRepo carPurchRepoObj;
	
	@Autowired
	private UsersRepository userRepoObj;
	
	@Autowired
	public CarModelsRepo carModelRepoObj;
	
	private static Long cancellationPeriod=(30L*24*60*60*1000);

	public CarPurchase getCarPurchaseBooking(Long carPrchBkID) throws PurchaseException{
		CarPurchase respObj=carPurchRepoObj.findById(carPrchBkID).orElse(null);
		if(respObj==null)
			throw new PurchaseException("Error Booking Not Present in Server");
		return respObj;
	}

	public List<CarPurchase> getCarPurchaseBookingList(Long userId)  throws PurchaseException{
		List<CarPurchase> lst=carPurchRepoObj.getAllCarPurchaseOfAUser(userId);
		if(lst==null)
			throw new PurchaseException("Error No List Of Car Purchase");
		
		return lst;
	}

	public CarPurchase carPurchBooking(Long userId, Long modelId, CarPurchase carPurchObj) {
		Users userObj=userRepoObj.findById(userId).orElse(null);
		CarModels carModelObj=carModelRepoObj.findById(modelId).orElse(null);
		carModelObj.setCarLots(carModelObj.getCarLots()-carPurchObj.getQuantity());
		CarModels savedDb=carModelRepoObj.save(carModelObj);
		System.out.println(userObj);
		System.out.println(savedDb);
		carPurchObj.setUser(userObj);
		carPurchObj.setModel(savedDb);
		CarPurchase responcarPurchBkObj=carPurchRepoObj.save(carPurchObj);
		return responcarPurchBkObj;
	//	return carPurchObj;
	}

	public void deleteCarPurchaseBooking(Long carPrchBkID, LocalDateTime currentTime) throws PurchaseException {
	    CarPurchase dBObj = carPurchRepoObj.findById(carPrchBkID).orElse(null);

	    if (dBObj == null) {
	        throw new PurchaseException("Error: Unable to Find Car Purchase ID");
	    }

	    LocalDateTime bookedDate = dBObj.getBookedDate();  // Assuming getDate() now returns LocalDateTime

	    // Calculate the difference in hours (or adjust as needed)
	    long differenceInHours = Duration.between(bookedDate, currentTime).toHours();

	    System.out.println("Current Time: " + currentTime +
	                       "\nBooked Time: " + bookedDate +
	                       "\nDifference (Hours): " + differenceInHours +
	                       "\nCancellation Period: " + cancellationPeriod);

	    if (differenceInHours > cancellationPeriod) {  // cancellationPeriod should match this unit
	        throw new PurchaseException("Error: Cancellation Period Expired");
	    }

	    dBObj.setIsVisible(false);
	    carPurchRepoObj.save(dBObj);
	}

//	public void deleteCarPurchaseBooking(Long carPrchBkID, Long currentTime)  throws PurchaseException
//	{
//		
//		CarPurchase dBObj=carPurchRepoObj.findById(carPrchBkID).orElse(null);
//		
//		Long bookedDate=null;
//		if(dBObj==null)
//			throw new PurchaseException("Error Unable to Find Car Purchase ID");
//		bookedDate=dBObj.getDate();
//		
//		
//		Long difference=currentTime-bookedDate;
//	
//		System.out.println("CurrentTime:"+currentTime+"\nBookedTime:"+bookedDate+"\nDiff:"+difference+"\nCancellation:"+cancellationPeriod);
//		
//		if(difference>cancellationPeriod)
//			throw new PurchaseException("Error Cancellation Period Expired");
//	
//		carPurchRepoObj.deleteById(carPrchBkID);
//	}
	
}

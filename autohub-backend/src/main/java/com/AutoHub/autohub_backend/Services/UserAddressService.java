package com.AutoHub.autohub_backend.Services;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AutoHub.autohub_backend.CustomExceptions.UserAddressException;
import com.AutoHub.autohub_backend.CustomExceptions.UserException;
import com.AutoHub.autohub_backend.Entities.UserAddresses;
import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.Repositories.UserAddressRepository;
import com.AutoHub.autohub_backend.Repositories.UsersRepository;

@Service
public class UserAddressService {

	@Autowired
	private UserAddressRepository userAddressRepoObj;

	@Autowired
	public UsersRepository userRepo;


	public UserAddresses addNewUserAddress(Long userId, UserAddresses userAddressObj) {
		
		Users user=userRepo.findById(userId).orElse(null);
	    userAddressObj.setUser(user);

		return userAddressRepoObj.save(userAddressObj);
	}


	public UserAddresses getUserAddresses(Long userId) {
		Users user=userRepo.findById(userId).orElse(null);
		System.err.println("Address from userAddresses:"+user.getAddressList()); 
		return user.getAddressList();
	}


	@Autowired
	private UserAddressRepository userAddRepObj;
	public void deleteByUserAddress(Long userId,Long addressId) throws UserAddressException {
		Users user=userRepo.findById(userId).orElse(null);
		
		try
		{
			userAddRepObj.deleteByIdCustom(addressId);
		}
		catch(Exception ex)
		{
			System.err.println(ex);
			return;
		}
//		List<UserAddresses> userAddressLst=user.getAddressList();
//		if(userAddressLst==null)
//		{
//			throw new UserAddressException("Error No Address Available");
//		}
//		List<UserAddresses> finalLst=new LinkedList<>();
//		for(UserAddresses obj : userAddressLst)
//		{
//			if(obj.getUserAddressId()!=addressId)
//			{
//				finalLst.add(obj);
//			}
//		}
//		user.setAddressList(finalLst);
//		userRepo.save(user);
	}


//	public UserAddresses updateUserAddress(Long userId, Long addressId, UserAddresses userAddressObj) 
//	throws UserAddressException{
//		Users user=userRepo.findById(userId).orElse(null);
//		List<UserAddresses> userAddressLst=user.getAddressList();
//		if(userAddressLst==null)
//		{
//			throw new UserAddressException("Error Address Unable to Update");
//		}
//		UserAddresses userAddresObjDB=null;
//		List<UserAddresses> finalLst=new LinkedList<>();
//		for(UserAddresses obj : userAddressLst)
//		{
//			if(obj.getUserAddressId()==addressId)
//			{
//				userAddresObjDB=obj;
//				continue;
//			}
//			finalLst.add(obj);
//		}
//		if(userAddresObjDB==null)
//		{
//			throw new UserAddressException("Error Address Unable to Update");
//		}
//		
//		Method[] methods=userAddresObjDB.getClass().getDeclaredMethods();
//		/**/
//		 Class<?> clazz = userAddresObjDB.getClass();
//		for(Method mtd:methods)
//		{
//			Object value=null;
//		    try
//		    {	
//		    	String methodName=mtd.getName();
//		    	if(methodName.startsWith("get") && !methodName.contains("FavoriteCars") && !methodName.contains("CarPurchased") && !methodName.contains("TestDriveBookings"))
//			    {
//		    		value=mtd.invoke(userAddressObj);
//		    		
//		    		if(value!=null)
//		    		{
//		    			String setter=methodName.replace("get", "set");
//		    			/**/
//		    			 Method setterMethod = clazz.getMethod(setter, value.getClass());
//		    	         setterMethod.invoke(userAddresObjDB, value);
//			       }
//			    }
//		    }
//		    catch (Exception e) {
//		    	throw new UserAddressException(e.getMessage());
//			}
//		}
//		
//		finalLst.add(userAddresObjDB);
//		user.setAddressList(finalLst);
//		Users responseObj=userRepo.save(user);
//		return userAddresObjDB;
//	}
	
	public UserAddresses updateUserAddress(Long userId, Long addressId, UserAddresses userAddressObj) 
			throws UserAddressException{
		System.out.println("Admin Address Update:Service");
				Users user=userRepo.findById(userId).orElse(null);
				UserAddresses userAddressLst=user.getAddressList();
				if(userAddressLst==null)
				{
					throw new UserAddressException("Error Address Unable to Update");
				}
				UserAddresses userAddresObjDB=userAddressLst;

				if(userAddresObjDB==null)
				{
					throw new UserAddressException("Error Address Unable to Update");
				}
				
				Method[] methods=userAddresObjDB.getClass().getDeclaredMethods();
				/**/
				 Class<?> clazz = userAddresObjDB.getClass();
				for(Method mtd:methods)
				{
					Object value=null;
				    try
				    {	
				    	String methodName=mtd.getName();
				    	if(methodName.startsWith("get") && !methodName.contains("FavoriteCars") && !methodName.contains("CarPurchased") && !methodName.contains("TestDriveBookings"))
					    {
				    		value=mtd.invoke(userAddressObj);
				    		
				    		if(value!=null)
				    		{
				    			String setter=methodName.replace("get", "set");
				    			/**/
				    			 Method setterMethod = clazz.getMethod(setter, value.getClass());
				    	         setterMethod.invoke(userAddresObjDB, value);
					       }
					    }
				    }
				    catch (Exception e) {
				    	throw new UserAddressException(e.getMessage());
					}
				}
				
			
				user.setAddressList(userAddresObjDB);
				Users responseObj=userRepo.save(user);
				return userAddresObjDB;
			}



	
	
	
}

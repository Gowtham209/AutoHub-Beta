package com.AutoHub.autohub_backend.Services;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.AutoHub.autohub_backend.CustomExceptions.CarModelException;
import com.AutoHub.autohub_backend.CustomExceptions.UserException;
import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.UserAddresses;
import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.Repositories.CarModelsRepo;
import com.AutoHub.autohub_backend.Repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	public UsersRepository userRepo;
	private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);
	@Autowired
	public CarModelsRepo cmr;
	
	public Users addNewUser(Users userObj) throws UserException
	{
		Users responseObj=null;
		List<Users> dbLst=userRepo.findAll();
		if(dbLst.size()==0)
		{
			userObj.setUserRole(1);
			System.out.println("Admin Creation");
		}
		else 
		{
			userObj.setUserRole(2);
			System.out.println("Customer Created");
		}

		try
		{
			 userObj.setPassword(encoder.encode(userObj.getPassword()));
			 responseObj=userRepo.save(userObj);
		}
		catch (Exception e) {
			throw new UserException("Error User Unable to SAVE");
		}
		return responseObj;
	}

	public List<Users> getUserList() {
		
		return userRepo.findByIsVisibleTrue();
	}

	public Users getUser(Long userId) throws UserException{
		
		Users userRespo=userRepo.findById(userId).orElse(null);
		if(userRespo==null)
		{
			throw new UserException("Error No User FOUND");
		}
		return userRespo;
	}

	public void deleteByUserId(Long userId) throws UserException{
		try {
			userRepo.deleteById(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UserException("Error Can't able to Delete User");
		}
	}

	public Users updateUser(Long userId, Users userObj) throws UserException{
		Users userDBObj=userRepo.findById(userId).orElse(null);
		if(userDBObj==null)
		{
			throw new UserException("Error No User FOUND");
		}
		Method[] methods=userDBObj.getClass().getDeclaredMethods();
		/**/
		 Class<?> clazz = userDBObj.getClass();
		for(Method mtd:methods)
		{
			Object value=null;
		    try
		    {	
		    	String methodName=mtd.getName();
		    	if(methodName.startsWith("get") && !methodName.contains("FavoriteCars") && !methodName.contains("CarPurchased") && !methodName.contains("TestDriveBookings"))
			    {
		    		value=mtd.invoke(userObj);
		    		
		    		if(value!=null)
		    		{
		    			String setter=methodName.replace("get", "set");
		    			/**/
		    			 Method setterMethod = clazz.getMethod(setter, value.getClass());
		    			 // The Above Line used to Retrive EXACT method that has the Expected VALUE Parameter
		    	         setterMethod.invoke(userDBObj, value);
			       }
			    }
		    }
		    catch (Exception e) {
		    	throw new UserException(e.getMessage());
			}
		}
		//userDBObj.setPassword(encoder.encode(userDBObj.getPassword()));
		Users reponseuserObj=userRepo.save(userDBObj);
		return reponseuserObj;
	}

	public void addFavorite(Long userId, Long modelId) throws UserException{
		CarModels carModel=cmr.findById(modelId).orElse(null);
		System.out.println("CarModel:"+carModel);
		if(carModel==null)
		{
			//return "No Car Model Present";
			throw new UserException("Error No Car Model Present");
		}
		
		Users users=userRepo.findById(userId).orElse(null);
		//System.out.println("Users:"+users);
		if(users==null)
		{
			//return "No user Present";
			throw new UserException("Error No User Present");
		}
		
		Set<CarModels> st = users.getFavoriteCars();
		st.add(carModel);
		users.setFavoriteCars(st);
		st.forEach(obj->System.out.println("Test"+obj));
		//Users usersVersion=userRepo.save(users);
		
	}
	
	public List<CarModels> getFavoriteCarList(Long userId)
	{
		List<CarModels> response=userRepo.fetchAllFavoriteCarsOfUser(userId);
		
		return response;
	}
}

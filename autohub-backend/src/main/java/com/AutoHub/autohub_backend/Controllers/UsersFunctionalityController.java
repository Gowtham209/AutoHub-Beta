package com.AutoHub.autohub_backend.Controllers;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AutoHub.autohub_backend.CustomExceptions.UserException;
import com.AutoHub.autohub_backend.DTO.CarModelsDTO;
import com.AutoHub.autohub_backend.DTO.UserAddressesDTO;
import com.AutoHub.autohub_backend.DTO.UsersDTO;
import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.UserAddresses;
import com.AutoHub.autohub_backend.Entities.Users;
import com.AutoHub.autohub_backend.SecurityConfig.JwtService;
import com.AutoHub.autohub_backend.SecurityConfig.UserDetailsServiceImpl;
import com.AutoHub.autohub_backend.Services.UserAddressService;
import com.AutoHub.autohub_backend.Services.UsersService;

@RestController
@RequestMapping("api/v1/autohub")
@CrossOrigin(origins="http://localhost:5173")
//@Tag(name = "User APIs", description = "Manage Users related functionalities in the AutoHub")
public class UsersFunctionalityController {
	
	@Autowired
	private UsersService userServObj;
	
	@Autowired
	private UserAddressService userAddressServObj;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/*
    GET API  http://localhost:8080/api/v1/autohub/favorite/user/{userid}
    				 	List Favorite Cars of a Individual users
   */
	//@Operation(summary = "Get All Favorite Cars", description = "Retrieve List Favorite Cars of a Individual users")
	@GetMapping("/favorite/user/{userId}")
	public ResponseEntity<Object> usersFavoriteCars(@PathVariable Long userId)
	{
			List<CarModels> lst=userServObj.getFavoriteCarList(userId);
			List<CarModelsDTO> reposne=lst.stream().map(obj -> modelMapper.map(obj, CarModelsDTO.class)).toList();
			return new ResponseEntity<>(reposne, HttpStatus.OK);
	}
	
	
	// COMMON API
	// FAVORITE
	
	/*
    POST API  http://localhost:8080/api/v1/autohub/addFavorite/{userId}/{modelId}
    					Used to Add a Car as a Favorite by a Individual users
   */
	//@Operation(summary = "Post a Favorite Car", description = "Create a Favorite Cars for a Individual users")
	@PostMapping(value="/addFavorite/{userId}/{modelId}")
	public ResponseEntity<Object> addFavorite(@PathVariable Long userId,@PathVariable Long modelId)
	{
	
		System.out.println("Favorite:"+userId+",modolId:"+modelId);
		try {
			userServObj.addFavorite(userId,modelId);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Successfully Added", HttpStatus.OK);
	}
	

	
    @Autowired
   private AuthenticationManager authManagerObj;
    
    @Autowired
    private JwtService jwtObj;
    
    @Autowired
    private UserDetailsServiceImpl useDetlServ;
    
    //Authentication
    /*
     
      POST API  http://localhost:8080/api/v1/autohub/login
      					Used to Login the User and Admin to Authenticate Those User and
      				 generate the JWT Token To Access further Services
     */
    
    //@Operation(summary = "Post User LOGIN", description = "Login of a Individual users")
    @PostMapping("/login")
    public ResponseEntity<Object> loginVerification(@RequestBody Users userObj)
    {
//    	Authentication authenticationObj=authManagerObj.authenticate(
//    			new UsernamePasswordAuthenticationToken(userObj.getEmailId(),userObj.getPassword())); 
//    	/*
//    	The Above Code calls the "public AuthenticationProvider authProvider()" method in "class ProjectSecurityConfig"
//	
//    	* */
//        if(authenticationObj.isAuthenticated())
//    	{
//    		String token=jwtObj.generateJwtToken(userObj.getEmailId());
//    		return token;
//    	}
//    	return "Error";
    	
    	Authentication authenticationObj=authManagerObj.authenticate(
    			new UsernamePasswordAuthenticationToken(userObj.getEmailId(),userObj.getPassword())); 
    	/*
    	The Above Code calls the "public AuthenticationProvider authProvider()" method in "class ProjectSecurityConfig"

    	* */
    	System.out.println("Login method:\n"+userObj.getEmailId()+" "+userObj.getPassword());
        if(authenticationObj.isAuthenticated())
    	{
        	Users obj=(Users) useDetlServ.loadUserByUsername(userObj);
    		String token=jwtObj.generateJwtToken(obj.getEmailId(),obj.getUserId(),obj.getUserRole());
        	//String token=jwtObj.generateJwtToken(userObj.getEmailId());
        	System.out.println("Pusha:\n"+obj);
        	return new ResponseEntity<>(token,HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<>("Invalid username or password. Please try again",HttpStatus.UNAUTHORIZED);
   }
    
    /*
    
    POST API  http://localhost:8080/api/v1/autohub/signup
    					Used to Signup New User and Admin to Access This WebApp Services
   */
//    @Operation(summary = "Post SIGN-UP", description = "Sign Up of a Individual users")
	@PostMapping("/signup")
	public ResponseEntity<Object> addUser(@RequestBody UsersDTO userDtoObj)
	{
		Users userObj=modelMapper.map(userDtoObj, Users.class);
		Users responseObj;
		try {
		responseObj = userServObj.addNewUser(userObj);
		} catch (UserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FAILED_DEPENDENCY);
		}
		UsersDTO responseUserDtoObj=modelMapper.map(responseObj, UsersDTO.class);
	return new ResponseEntity<>(responseUserDtoObj, HttpStatus.OK);
	}
    
    //ADMIN
    /*
    GET API  http://localhost:8080/api/v1/autohub/admin/users
    					Used to List Users of the WebApp Only by the Admin
   */
    
	//@Operation(summary = "Get All Users", description = "Retrieve List Users Only by the Admin")
	@GetMapping("/admin/users")
	public ResponseEntity<Object> getUserList()
	{
		
		
		List<Users> userLst=userServObj.getUserList();

		List<UsersDTO> responseUserLst=new LinkedList<>();
		
		responseUserLst=userLst.stream().map(obj-> modelMapper.map(obj,UsersDTO.class)).toList();
		return new ResponseEntity<>(responseUserLst, HttpStatus.OK);	
	}
	
    /*
    GET API  http://localhost:8080/api/v1/autohub/admin/{userId}
    					Used to Give AdminUser Detail
   */
	//@Operation(summary = "Get A Specific User", description = "Retrieve A Users Only by the Admin")
	@GetMapping("/admin/{userId}")
	public ResponseEntity<Object> getAdminUser(@PathVariable("userId") Long userId)
	{
		UsersDTO responseUser=null;
		Users user;
		try {
			user = userServObj.getUser(userId);
		} catch (UserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		responseUser=modelMapper.map(user,UsersDTO.class);
		return new ResponseEntity<>(responseUser, HttpStatus.OK);	
	}
	
	/*
    DELETE API  http://localhost:8080/api/v1/autohub/admin/user/{userId}
    					Used to remove a Particular User by ADMIN Only
   */
	//@Operation(summary = "Delete A Specific User", description = "Remove a User Only by the Admin")
	@DeleteMapping("/admin/user/{userId}")
	public ResponseEntity<Object> userDelete(@PathVariable("userId") Long userId)
	{
		try
		{
			userServObj.deleteByUserId(userId);
		}
		catch (UserException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfull Deleted", HttpStatus.OK);
	}
	
	/*
    PUT API  http://localhost:8080/api/v1/autohub/admin/{userId}
    					Used to Update a details of ADMIN Only
   */
	//@Operation(summary = "Put A Admin User", description = "Update a Specific Admin User Details")
	@PutMapping("/admin/{userId}")
	public ResponseEntity<Object> updateAdminUser(@PathVariable("userId") Long userId,@RequestBody UsersDTO userDtoObj)
	{
		Users userObj=modelMapper.map(userDtoObj, Users.class);
		System.out.println("UserUpdate Controller:\n"+userObj);
		Users responseObj;
		try {
			responseObj = userServObj.updateUser(userId,userObj);
		} catch (UserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		UsersDTO responseUserDtoObj=modelMapper.map(responseObj, UsersDTO.class);
		return new ResponseEntity<>(responseUserDtoObj, HttpStatus.OK);
	}
	
	/*
    POST API  http://localhost:8080/api/v1/autohub/admin/{userId}/address
    					Used to add Address details to a particular ADMIN 
   */
	//@Operation(summary = "Post Single Admin User's Address", description = "Add Address to Specific Admin User")
	@PostMapping("/admin/{userId}/address")
	public ResponseEntity<Object> addAdminUserAddress(@PathVariable("userId") Long userId,@RequestBody UserAddressesDTO userAddressDtoObj)
	{
		UserAddresses userAddressObj=modelMapper.map(userAddressDtoObj, UserAddresses.class);
		System.out.println("Address:\n"+userAddressObj);
		UserAddresses responseObj;
		try {
			responseObj = userAddressServObj.addNewUserAddress(userId,userAddressObj);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		UserAddressesDTO responseUserDtoObj=null;
		responseUserDtoObj= modelMapper.map(responseObj, UserAddressesDTO.class);
		
		
		return new ResponseEntity<>(responseUserDtoObj, HttpStatus.OK);
	}
	
	/*
    GET API  http://localhost:8080/api/v1/autohub/admin/{userId}/addresses
    					Used to Get List of Address details of a particular ADMIN 
   */
	
	@GetMapping("/admin/{userId}/addresses")
	public ResponseEntity<Object> getAdminUserAddress(@PathVariable("userId") Long userId)
	{
		UserAddresses responseObj;
		try {
			responseObj = userAddressServObj.getUserAddresses(userId);
			System.out.println("Address get Controller:"+responseObj);
			if(responseObj==null)
			{
				return new ResponseEntity<>("No Address", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		UserAddressesDTO responseUserDtoObj=modelMapper.map(responseObj, UserAddressesDTO.class);
				
		return new ResponseEntity<>(responseUserDtoObj, HttpStatus.OK);
	}
	
	/*
    DELETE API  http://localhost:8080/api/v1/autohub/admin/{userId}/addresse/{addressId}
    					Used to DELETE a particular Address details of a particular ADMIN 
   */
	@DeleteMapping("/admin/{userId}/address/{addressId}")
	public ResponseEntity<Object> deleteAdminUserAddress(@PathVariable("userId") Long userId,
			@PathVariable("addressId") Long addressId)
	{
		try
		{
			userAddressServObj.deleteByUserAddress(userId,addressId);
		}
		catch (Exception e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfull Deleted", HttpStatus.OK);

	}
	
	/*
    PUT API  http://localhost:8080/autohub/api/v1/autohub/{userId}/addresse/{addressId}
    					Used to UPDATE a particular Address details of a particular ADMIN 
   */
	@PutMapping("/admin/{userId}/address/{addressId}")
	public ResponseEntity<Object> deleteAdminUserAddress(@PathVariable("userId") Long userId,
			@PathVariable("addressId") Long addressId,@RequestBody UserAddressesDTO userAddressDtoObj)
	{
		UserAddresses userAddressObj=modelMapper.map(userAddressDtoObj, UserAddresses.class);
		System.out.println("Admin Address Update:Controller");
		UserAddresses responseObj;
		try {
			responseObj = userAddressServObj.updateUserAddress(userId,addressId,userAddressObj);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	
		UserAddressesDTO addressObj=modelMapper.map(responseObj, UserAddressesDTO.class);

		return new ResponseEntity<>(addressObj, HttpStatus.OK);
	}
	
	
	//USERS
	
    /*
    GET API  http://localhost:8080/api/v1/autohub/user/{userId}
    					Used to give User Detail
   */
	@GetMapping("/user/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable("userId") Long userId)
	{
		System.out.println("User Detail API Hitted");
		UsersDTO responseUser=null;
		Users user;
		try {
			user = userServObj.getUser(userId);
		} catch (UserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		responseUser=modelMapper.map(user,UsersDTO.class);
		return new ResponseEntity<>(responseUser, HttpStatus.OK);	
	}
	
	/*
    PUT API  http://localhost:8080/api/v1/autohub/user/{userId}
    					Used to Update a details of USER Only
   */
	@PutMapping("/user/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable("userId") Long userId,@RequestBody UsersDTO userDtoObj)
	{
		Users userObj=modelMapper.map(userDtoObj, Users.class);
		System.out.println("UserUpdate Controller:\n"+userObj);
		Users responseObj;
		try {
			responseObj = userServObj.updateUser(userId,userObj);
		} catch (UserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		UsersDTO responseUserDtoObj=modelMapper.map(responseObj, UsersDTO.class);
		return new ResponseEntity<>(responseUserDtoObj, HttpStatus.OK);
	}
	
	/*
    POST API  http://localhost:8080/api/v1/autohub/user/{userId}/address
    					Used to add Address details to a particular User
   */
	@PostMapping("/user/{userId}/address")
	public ResponseEntity<Object> addUserAddress(@PathVariable("userId") Long userId,@RequestBody UserAddressesDTO userAddressDtoObj)
	{
		UserAddresses userAddressObj=modelMapper.map(userAddressDtoObj, UserAddresses.class);
		System.out.println("API Address Hitted:\n"+userAddressObj);
		UserAddresses responseObj;
		try {
			responseObj = userAddressServObj.addNewUserAddress(userId,userAddressObj);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

			UserAddressesDTO addressObj=modelMapper.map(responseObj, UserAddressesDTO.class);

		return new ResponseEntity<>(addressObj, HttpStatus.OK);
	}
	
	/*
    GET API  http://localhost:8080/api/v1/autohub/user/{userId}/addresses
    					Used to Get List of Address details of a particular User 
   */
	@GetMapping("/user/{userId}/addresses")
	public ResponseEntity<Object> getUserAddress(@PathVariable("userId") Long userId)
	{
		UserAddresses responseObj;
		try {
			responseObj = userAddressServObj.getUserAddresses(userId);
			System.out.println("Address get Controller:"+responseObj);
			if(responseObj==null)
			{
				return new ResponseEntity<>("No Address", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		UserAddressesDTO responseUserDtoObj=modelMapper.map(responseObj, UserAddressesDTO.class);
		return new ResponseEntity<>(responseUserDtoObj, HttpStatus.OK);
	}
	
	/*
    DELETE API  http://localhost:8080/api/v1/autohub/user/{userId}/addresse/{addressId}
    					Used to DELETE a particular Address details of a particular USER 
   */
	@DeleteMapping("/user/{userId}/address/{addressId}")
	public ResponseEntity<Object> deleteUserAddress(@PathVariable("userId") Long userId,
			@PathVariable("addressId") Long addressId)
	{
		try
		{
			userAddressServObj.deleteByUserAddress(userId,addressId);
		}
		catch (Exception e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfull Deleted", HttpStatus.OK);

	}
	
	/*
    PUT API  http://localhost:8080/api/v1/autohub/user/{userId}/addresse/{addressId}
    					Used to UPDATE a particular Address details of a particular USER 
   */
	@PutMapping("/user/{userId}/address/{addressId}")
	public ResponseEntity<Object> deleteUserAddress(@PathVariable("userId") Long userId,
			@PathVariable("addressId") Long addressId,@RequestBody UserAddressesDTO userAddressDtoObj)
	{
		UserAddresses userAddressObj=modelMapper.map(userAddressDtoObj, UserAddresses.class);
		System.out.println("User Address Update:Controller");
		UserAddresses responseObj;
		try {
			responseObj = userAddressServObj.updateUserAddress(userId,addressId,userAddressObj);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	
		UserAddressesDTO addressObj=modelMapper.map(responseObj, UserAddressesDTO.class);

		return new ResponseEntity<>(addressObj, HttpStatus.OK);
	}
}

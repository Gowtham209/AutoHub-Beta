package com.AutoHub.autohub_backend.Utility;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;

import com.AutoHub.autohub_backend.Entities.Users;


public class Utility {

	public static String roleMapper(Integer role) {
		if(role==1)
			return "SUPER_ADMIN";
		else if(role==2)
			return "ADMIN";
		return "USER";
	}


	
	/*public static String roleMapper(Byte role)
	{
		if(role==1)
			return "SUPER_ADMIN";
		else if(role==2)
			return "ADMIN";
		return "USER";
	}
	
	public Users updateUser(Long userId, Users user) {
		Users dbUser=userRepo.findById(userId).orElse(null);
		
		if(dbUser==null)
			return null;
		
		Method[] methods=user.getClass().getDeclaredMethods();
		
		
		 Class<?> clazz = dbUser.getClass();
		for(Method mtd:methods)
		{
			Object value=null;
		    try
		    {
		    	
		    	String methodName=mtd.getName();
		    	if(methodName.startsWith("get"))
			    {
		    		value=mtd.invoke(user);
		    		
		    		if(value!=null)
		    		{
		    			String setter=methodName.replace("get", "set");
		    			
		    			 Method setterMethod = clazz.getMethod(setter, value.getClass());
		    	         setterMethod.invoke(dbUser, value);
			       }
			    }
		    }
		    catch (Exception e) {
		    	System.out.println(mtd.getName()+" error \n"+e+" = "+mtd.getName());
			}
		}
		
		return dbUser;
	}*/
	
}

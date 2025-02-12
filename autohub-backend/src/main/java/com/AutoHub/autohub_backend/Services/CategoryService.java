package com.AutoHub.autohub_backend.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AutoHub.autohub_backend.CustomExceptions.CategoryException;
import com.AutoHub.autohub_backend.Entities.Category;
import com.AutoHub.autohub_backend.Repositories.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepoObj;
	
	public Category addNewCategory(Category categoryObj) throws CategoryException
	{
			Category cateDbObj=categoryRepoObj.findByCarType(categoryObj.getCarType());
			if(cateDbObj!=null)
			{
				throw new CategoryException("Error Category Already Present");
			}
			Category cateRespObj=categoryRepoObj.save(categoryObj);
			return cateRespObj;
	}

	public void deleteByCategoryId(Long cateId) throws CategoryException
	{
		Category cateDbObj=categoryRepoObj.findById(cateId).orElse(null);
		cateDbObj.setIsVisible(false);
		if(cateDbObj==null)
		{
			throw new CategoryException("Error Category Not Present in DB");
		}
		categoryRepoObj.save(cateDbObj);
	}

	public Category updateCategory(String carType,Long cateID) throws CategoryException{
		Category cateDbObj=categoryRepoObj.findById(cateID).orElse(null);
		if(cateDbObj==null)
			throw new CategoryException("Error Can't Delete ID");
		cateDbObj.setCarType(carType);
		Category responseCate=null;
		try {
		 responseCate=categoryRepoObj.save(cateDbObj);
		}
		catch(Exception e)
		{
			throw new CategoryException(e.getMessage());
		}
		return responseCate;
	}

	public List<Category> categoriesList() {
		List<Category> lst=categoryRepoObj.getAllCategories();
		return lst;
	}

	public Category getCategory(Long cateId) throws CategoryException {
		Category cateDbObj=categoryRepoObj.findById(cateId).orElse(null);
		if(cateDbObj==null)
		{
			throw new CategoryException("Error Category ID Not Present");
		}
		return cateDbObj;
	}
}

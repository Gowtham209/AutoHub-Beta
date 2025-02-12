package com.AutoHub.autohub_backend.Controllers;

import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AutoHub.autohub_backend.CustomExceptions.CarModelException;
import com.AutoHub.autohub_backend.CustomExceptions.CategoryException;
import com.AutoHub.autohub_backend.DTO.CarModelsDTO;
import com.AutoHub.autohub_backend.DTO.CategoryDTO;
import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.Category;
import com.AutoHub.autohub_backend.Services.CarModelService;
import com.AutoHub.autohub_backend.Services.CategoryService;

@RestController
@RequestMapping("api/v1/autohub")
@CrossOrigin(origins="http://localhost:5173")
public class CarFunctionalityController {
	
	@Autowired
	private CategoryService cateServObj;
	
	@Autowired
	private CarModelService carModelServObj;
	
	@Autowired
	private ModelMapper modelMapper;
		
	
	//ADMIN
	
	/*
	 
	 POSTAPI http://localhost:8080/api/v1/autohub/admin/car
	 					Used to POST a Car Record in the Table By ADMIN
	  
	 */
	@PostMapping(value="/admin/car", consumes = "application/json")
	public ResponseEntity<Object> addCar(@RequestBody CarModelsDTO carmodelDTO)
	{
	
		CarModels carModelObj = modelMapper.map(carmodelDTO, CarModels.class);

		System.out.println("POST CarModel\n"+carModelObj);
		CarModels reponseObj;
		try {
			reponseObj = carModelServObj.addCarModel(carModelObj);
		} catch (CarModelException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(reponseObj, HttpStatus.OK);	
		}
	
	/*
	 
	 POSTAPI http://localhost:8080/api/v1/autohub/admin/car
	 					Used to POST a Batch of Cars Records into the Table ONLY By ADMIN
	  
	 */
	@PostMapping(value="/admin/cars", consumes = "application/json")
	public ResponseEntity<Object> addCarList(@RequestBody List<CarModelsDTO> carmodelsDTO)
	{
		List<CarModels> carList=carmodelsDTO.stream().map(obj -> modelMapper.map(obj, CarModels.class)).toList();
		Integer carsAddCount;
		try {
			carsAddCount = carModelServObj.addCarModelList(carList);
		} catch (CarModelException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(carsAddCount, HttpStatus.OK);	 
	}
	
	/*
	 Delete API http://localhost:8080/api/v1/autohub/admin/car/{carId}
	 					Used to Delete a Particular Car Record from the Table ONLY By ADMIN
	  
	 */
	@DeleteMapping("/admin/car/{carId}")
	public ResponseEntity<Object> carModelDelete(@PathVariable("carId") Long carId)
	{
		System.out.println("Delete car Controller");
		try
		{
			carModelServObj.deleteByCarModelId(carId);
		}
		catch (CarModelException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfull Deleted", HttpStatus.OK);
	}
	
	/*
	 Put API http://localhost:8080/api/v1/autohub/admin/car/{carId}
	 					Used to Update a Particular Car Record from the Table ONLY By ADMIN 
	  
	 */
	@PutMapping(value="/admin/car/{carId}",consumes = "application/json")
	public ResponseEntity<Object> updateCarModel(@PathVariable("carId") Long carId,@RequestBody CarModelsDTO carmodelDTO)
	{		
		CarModels carModelObj = modelMapper.map(carmodelDTO, CarModels.class);
		
		System.out.println("Controller UPDATE CarModel\n"+carModelObj);
		CarModels reponseObj;
		try {
			reponseObj = carModelServObj.updateCarModel(carModelObj,carId);
		} catch (CarModelException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(reponseObj, HttpStatus.OK);	 
	}
	
	// CATEGORY

	/*
	 
	 POSTAPI http://localhost:8080/api/v1/autohub/admin/category
	 					Used to POST a Category Type for Cars Categorizing ONLY By ADMIN
	  
	 */
	@PostMapping(value="/admin/category",consumes = "application/json")
	public ResponseEntity<Object> addNewCategory(@RequestBody CategoryDTO cateDtoObj)
	{		
		Category categ = modelMapper.map(cateDtoObj, Category.class);
		Category categCreated =null;
		try
		{
			categCreated = cateServObj.addNewCategory(categ);
		}
		catch (CategoryException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		CategoryDTO responseObj=modelMapper.map(categCreated,CategoryDTO.class);
		return new ResponseEntity<>(responseObj, HttpStatus.OK);
	}
	
	/*
	 Delete API http://localhost:8080/api/v1/autohub/admin/category/{cateID}
	 					Used to Delete a Particular Category in the Table ONLY By ADMIN
	 */
	@DeleteMapping("/admin/category/{cateID}")
	public ResponseEntity<Object> categoryDelete(@PathVariable("cateID") Long cateId)
	{
		try
		{
			cateServObj.deleteByCategoryId(cateId);
		}
		catch (CategoryException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Successfull Deleted", HttpStatus.OK);
	}
	
	/*
	 Put API http://localhost:8080/api/v1/autohub/admin/category/{cateID}
	 					Used to Update a Particular Category Record of the Category Table ONLY By ADMIN
	 */
	@PutMapping("/admin/category/{cateID}")
	public ResponseEntity<Object> updateCategory(@PathVariable("cateID") Long cateId,@RequestBody CategoryDTO cateDtoObj)
	{		
		Category categ = modelMapper.map(cateDtoObj, Category.class);
		Category categCreated =null;
		try
		{
			categCreated = cateServObj.updateCategory(categ.getCarType(),cateId);
		}
		catch (CategoryException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		CategoryDTO responseObj=modelMapper.map(categCreated,CategoryDTO.class);
		return new ResponseEntity<>(responseObj, HttpStatus.OK);
	}
	
	//PUBLIC API
	
	/*
	 Get API http://localhost:8080/api/v1/autohub/categories
	 					Used to Get LIST of Category Records from the Category Table By Both Admin and User
	 */
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> categoriesList()
	{
		List<Category> cateLst=cateServObj.categoriesList();
		List<CategoryDTO> responseLst=cateLst.stream().map(obj -> modelMapper.map(obj, CategoryDTO.class)).toList();
		return new  ResponseEntity<>(responseLst, HttpStatus.OK);
	}
	
	/*
	 Get API http://localhost:8080/api/v1/autohub/category/{cateID}
	 					Used to Get a Particular Category Records from the Category Table By Both Admin and User
	 */
	@GetMapping("/category/{cateID}")
	public ResponseEntity<Object> categoriesList(@PathVariable("cateID") Long cateId)
	{
		Category cateObj;
		try {
			cateObj = cateServObj.getCategory(cateId);
		} catch (CategoryException e) {
			 return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		CategoryDTO responseObj=modelMapper.map(cateObj,CategoryDTO.class);
		return new  ResponseEntity<>(responseObj, HttpStatus.OK);
	}
	
	
	/*
	 Get API http://localhost:8080/api/v1/autohub/car/{carID}
	 					Used to Get a Particular Car Model Records from the Car Model Table By Both Admin and User
	 */
	@GetMapping(value="/car/{carId}")
	public ResponseEntity<Object> findCarModel(@PathVariable("carId") Long carId)
	{
		CarModels reponseObj;
		try {
			reponseObj = carModelServObj.getCarModel(carId);
		} catch (CarModelException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(reponseObj, HttpStatus.OK);	 
	}
	
	
	/*
	 Get API http://localhost:8080/api/v1/autohub/cars
	 					Used to Get a List of Car Models Records from the Car Model Table By Both Admin and User
	 */
	@GetMapping("/cars")
	public ResponseEntity<Object> findAllCarModel()
	{
		System.out.println("Api Hitted CARS list");
		List<CarModels> reponseObjLst=new LinkedList<>();
		reponseObjLst = carModelServObj.getAllCarModel();
		List<CarModelsDTO> carList=reponseObjLst.stream().map(obj-> modelMapper.map(obj,CarModelsDTO.class)).toList();
		return new ResponseEntity<>(carList, HttpStatus.OK);	 
	}
	

}

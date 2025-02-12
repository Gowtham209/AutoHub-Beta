package com.AutoHub.autohub_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.AutoHub.autohub_backend.CustomExceptions.CategoryException;
import com.AutoHub.autohub_backend.Entities.Category;
import com.AutoHub.autohub_backend.Repositories.CategoryRepo;
import com.AutoHub.autohub_backend.Services.CategoryService;

@SpringBootTest
class AutohubBackendApplicationTests {

	    @Mock
	    private CategoryRepo categoryRepoObj;

	    @InjectMocks
	    private CategoryService categoryService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testCategoriesList() {
	        List<Category> categories = Arrays.asList(new Category(), new Category());
	        when(categoryRepoObj.getAllCategories()).thenReturn(categories);

	        List<Category> result = categoryService.categoriesList();

	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(categoryRepoObj).getAllCategories();
	    }

	    @Test
	    public void testGetCategory_Success() throws CategoryException {
	        Long cateId = 1L;
	        Category category = new Category();
	        when(categoryRepoObj.findById(cateId)).thenReturn(Optional.of(category));

	        Category result = categoryService.getCategory(cateId);

	        assertNotNull(result);
	        assertEquals(category, result);
	        verify(categoryRepoObj).findById(cateId);
	    }

	    @Test
	    public void testGetCategory_NotFound() {
	        Long cateId = 1L;
	        when(categoryRepoObj.findById(cateId)).thenReturn(Optional.empty());

	        CategoryException exception = assertThrows(CategoryException.class, () -> {
	            categoryService.getCategory(cateId);
	        });

	        assertEquals("Error Category ID Not Present", exception.getMessage());
	        verify(categoryRepoObj).findById(cateId);
	    }

}

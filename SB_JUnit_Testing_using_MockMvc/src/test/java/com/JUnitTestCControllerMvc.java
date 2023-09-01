package com;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.repository.Product_Repository;

@WebMvcTest   //  Bcoz we can't use Postman here to test 
class JUnitTestCControllerMvc {

	private static final String PRODUCT_URL = "/product_info/products";

	private static final String CONTEXT_URL = "/product_info";

	private static final int PRODUCT_PRICE = 1000;

	private static final String PRODUCT_DESCRIPTION = "It's nice";

	private static final String PRODUCT_NAME = "MacBook";

	private static final int PRODUCT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private Product_Repository productRepo;
	
	private Product buildProduct() {
		Product product = new Product();
		product.setId(PRODUCT_ID);
		product.setName(PRODUCT_NAME);
		product.setDescription(PRODUCT_DESCRIPTION);
		product.setPrice(PRODUCT_PRICE);
		return product;
	}

//=================================================================================================================================

	@Test
	public void test_getAll() throws Exception {
		Product product = buildProduct();

		List<Product> products = Arrays.asList(product);
		when(productRepo.findAll()).thenReturn(products);  // return products
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		mockMvc.perform(get(PRODUCT_URL +"/getAll")
			   .contextPath(CONTEXT_URL)).andExpect(status().isOk())
			   .andExpect(content().json(objectWriter.writeValueAsString(products)));
	}
	
//================================================================================================================================
	
	@Test
	public void test_createProduct() throws JsonProcessingException, Exception {
		Product product = buildProduct();
		
		when(productRepo.save(any())).thenReturn(product);  // return one product
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(post(PRODUCT_URL + "/store")
			   .contextPath(CONTEXT_URL).contentType(MediaType.APPLICATION_JSON)
			   .content(objectWriter.writeValueAsString(product))).andExpect(status().isOk())
			   .andExpect(content().json(objectWriter.writeValueAsString(product)));
	}
	
//================================================================================================================================
	
	@Test
	public void test_updateProduct() throws JsonProcessingException, Exception {
		Product product = buildProduct();
		product.setPrice(2000);
		
		when(productRepo.save(any())).thenReturn(product);  // return one product
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(put(PRODUCT_URL + "/update")
			   .contextPath(CONTEXT_URL).contentType(MediaType.APPLICATION_JSON)
			   .content(objectWriter.writeValueAsString(product))).andExpect(status().isOk())
			   .andExpect(content().json(objectWriter.writeValueAsString(product)));
	}
	
//================================================================================================================================
	
	@Test
	public void test_deleteProduct() throws Exception {
		doNothing().when(productRepo).deleteById(PRODUCT_ID);
		
		mockMvc.perform(delete(PRODUCT_URL + "/delete/" +PRODUCT_ID)
			   .contextPath(CONTEXT_URL)).andExpect(status().isOk());
	}
	
//=================================================================================================================================
//=================================================================================================================================
	
	

}

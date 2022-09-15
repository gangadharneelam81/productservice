package com.productservice.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.constants.ApplicationConstants;
import com.productservice.entity.Product;
import com.productservice.exception.NoDataFoundException;
import com.productservice.exception.ProductNotFoundException;
import com.productservice.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
    ProductService productService;
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product) throws Exception {
		log.info(ApplicationConstants.PRODUCT_ID +product.getId());
		String response = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @GetMapping("/products")
    public List<Product> findAllProducts() throws NoDataFoundException {
    	log.info("getAllProducts Method in ProductController Class ");
        return productService.getAllProducts();
    }

    @GetMapping("/productById/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable int id)  throws ProductNotFoundException  {
    	log.info(ApplicationConstants.PRODUCT_ID +id);
    	Optional<Product> product = productService.getProductById(id);
    	return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @GetMapping("/product/{name}")
    public ResponseEntity<Product> findProductByName(@PathVariable String name) throws ProductNotFoundException  {
    	log.info(ApplicationConstants.PRODUCT_NAME +name);
    	Optional<Product> product = Optional.ofNullable(productService.getProductByName(name));
    	return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product)  throws Exception {
    	log.info(ApplicationConstants.PRODUCT_ID +product.getId());
    	return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) throws Exception {
    	String response = productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

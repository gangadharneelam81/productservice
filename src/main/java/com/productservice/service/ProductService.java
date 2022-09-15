package com.productservice.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.constants.ApplicationConstants;
import com.productservice.entity.Product;
import com.productservice.exception.NoDataFoundException;
import com.productservice.exception.ProductNotFoundException;
import com.productservice.repository.ProductRepository;

@Service
public class ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	ProductRepository productRepository;

	public List<Product> getAllProducts() throws NoDataFoundException {
		log.info("getAllProducts Mehtod Entered");
  	  List < Product > productList = productRepository.findAll();
  	  if (productList.isEmpty()) {
            throw new NoDataFoundException();
        }
  	  log.info("getAllProducts Mehtod Closed");
        return productList;
	}

	public String createProduct(Product product) throws Exception{

		try {
			productRepository.save(product);
  	  }catch(Exception ex) {
  		  log.info("Exception While Saving Product "+ex.getMessage()); 
  		  return ApplicationConstants.FAILURE_MESSAGE+product.getProductName();
  	  }
     
      return ApplicationConstants.SUCCESS_MESSAGE+product.getProductName();
	}

	public Optional< Product > getProductById(int id) throws ProductNotFoundException {
  	  log.info("getProduct Mehtod Entered");
  	  Optional< Product > product = productRepository.findById(id);
  	  if(!product.isPresent()) {
  		log.info("getProduct Mehtod Entered");
  		  throw new ProductNotFoundException(id);
  	  }
  	  log.info("getProduct Mehtod Closed");
        return product;
    }

	public Product getProductByName(String name) {
		return productRepository.findByProductName(name);
	}

	public String deleteProduct(int id) {
		productRepository.deleteById(id);
		return "product removed !! " + id;
	}

	public Product updateProduct(Product product) {
		
		return productRepository.save(product);
	}

}

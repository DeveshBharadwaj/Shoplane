package com.cfs.Ecomm.service;

import com.cfs.Ecomm.model.Product;
import com.cfs.Ecomm.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct()
    {
        return productRepository.findAll();
    }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product)
    {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }


    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
}

package com.alpha.tinderware.controller;

import com.alpha.tinderware.dao.ProductDAO;
import com.alpha.tinderware.dto.ProductBottomWear;
import com.alpha.tinderware.dto.ProductTopWear;
import com.alpha.tinderware.dao.BottomWearRepository;
import com.alpha.tinderware.dao.MatchParametersRepository;
import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.dao.TopWearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/product/controller")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private MatchParametersRepository matchParametersRepository;
    @Autowired
    private BottomWearRepository bottomWearRepository;
    @Autowired
    private TopWearRepository topWearRepository;

    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){

        productDAO.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/showProducts")
    public List<Product> getAllProducts(){

        return productDAO.findAll();
    }

    @GetMapping("/getProduct/{id}")
    public Product getProductById(@PathVariable int id){

        return productDAO.findById(id).orElse(null);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {

        // Find the product
        Optional<Product> productOpt = productDAO.findById(id);

        if (productOpt.isEmpty()) {
            return new ResponseEntity<>("No Product has been found!", HttpStatus.NOT_FOUND);
        }

        // Delete related TopWear, BottomWear, and MatchParameters
        topWearRepository.findByIdp(id).ifPresent(topWearRepository::delete);
        bottomWearRepository.findByIdp(id).ifPresent(bottomWearRepository::delete);
        matchParametersRepository.findByIdp(id).ifPresent(matchParametersRepository::delete);

        // Delete the product
        productDAO.delete(productOpt.get());

        return new ResponseEntity<>("Product has been deleted!", HttpStatus.OK);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestBody Product updatedProduct) {

        Optional<Product> existingProductOpt = productDAO.findById(id);

        if (existingProductOpt.isEmpty()) {
            return new ResponseEntity<>("No product has been found!", HttpStatus.NOT_FOUND);
        }

        Product existingProduct = existingProductOpt.get();

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setStyle(updatedProduct.getStyle());
        existingProduct.setFit(updatedProduct.getFit());
        existingProduct.setMaterial(updatedProduct.getMaterial());
        existingProduct.setColor(updatedProduct.getColor());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDiscount(updatedProduct.getDiscount());
        existingProduct.setRating(updatedProduct.getRating());
        existingProduct.setOccasion(updatedProduct.getOccasion());
        existingProduct.setSize(updatedProduct.getSize());
        existingProduct.setImagePath(updatedProduct.getImagePath());
        existingProduct.setLink(updatedProduct.getLink());
        existingProduct.setGender(updatedProduct.getGender());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setArticleType(updatedProduct.getArticleType());





        productDAO.save(existingProduct);

        return new ResponseEntity<>("Product has been successfully updated!", HttpStatus.OK);
    }

    @PostMapping("/addTopWear")
    public String addTopWear(@RequestBody ProductTopWear topWearModel){

        Product product = productDAO.save(topWearModel.getProduct());
        int lastProductId = product.getId();

        topWearModel.getTopWear().setIdp(lastProductId);
        topWearRepository.save(topWearModel.getTopWear());

        topWearModel.getMatchParameters().setIdp(lastProductId);
        matchParametersRepository.save(topWearModel.getMatchParameters());

        return "Product has been added successfully in Product Table with ID = " + lastProductId;
    }

    @PostMapping("/addBottomWear")
    public String addBottomWear(@RequestBody ProductBottomWear bottomWearModel){

        Product product = productDAO.save(bottomWearModel.getProduct());
        int lastProductId = product.getId();

        bottomWearModel.getBottomWear().setIdp(lastProductId);
        bottomWearRepository.save(bottomWearModel.getBottomWear());

        bottomWearModel.getMatchParameters().setIdp(lastProductId);
        matchParametersRepository.save(bottomWearModel.getMatchParameters());

        return "Product has been added successfully in Product Table with ID = " + lastProductId;


    }

}

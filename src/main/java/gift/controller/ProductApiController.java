package gift.controller;

import gift.dto.ProductAddRequest;
import gift.dto.ProductResponse;
import gift.dto.ProductUpdateRequest;
import gift.exception.ProductException;
import gift.model.Product;
import gift.repository.ProductDao;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {

    private final ProductDao productDao;

    @Autowired
    public ProductApiController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        List<Product> productsList = productDao.getAllProducts();
        if(productsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        List<ProductResponse> dtoList = productsList.stream()
            .map(ProductResponse::new)
            .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {

        Product product = productDao.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        ProductResponse productResponse = new ProductResponse(product);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/api/products")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductAddRequest dto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProductException(bindingResult.getAllErrors());
        }

        Product product = new Product(dto.getName(),
            dto.getPrice(), dto.getImageUrl());
        productDao.insertProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/products")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductUpdateRequest dto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProductException(bindingResult.getAllErrors());
        }

        Product updatedProduct = new Product(dto.getId(), dto.getName(),
            dto.getPrice(), dto.getImageUrl());
        productDao.updateProduct(updatedProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/products")
    public ResponseEntity<Product> deleteProduct(@RequestParam("id") Long id) {
        productDao.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

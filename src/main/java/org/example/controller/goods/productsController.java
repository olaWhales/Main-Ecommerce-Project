package org.example.controller.goods;

import org.example.dto.request.goodsRequest.productRequest.*;
import org.example.dto.response.goodsResponse.productResponse.ProductResponse;
import org.example.services.goods.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/e_commerce/api/products")
public class productsController {

    @Autowired
    private ProductService productService;

    @CrossOrigin(origins = "*")
    @PostMapping("/create_product/")
    public ResponseEntity<?> createProduct( @RequestBody CreateProductRequest createProductRequest) {
//        Long sellerId = createProductRequest.getId();
        try {
            ProductResponse productResponse = productService.createProduct(createProductRequest);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin (origins = "*")

    @PutMapping("/update-product/")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
//        Long id = updateProductRequest.getId();
//        Long sellerId = updateProductRequest.getId();
        try {
            return new ResponseEntity<>(productService.updateProduct(updateProductRequest), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/product/delete/api/")
    public ResponseEntity<?> deleteProduct(@RequestBody DeleteProductRequest deleteProductRequest) {
        try {
            productService.deleteProduct(deleteProductRequest);
            return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the product.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@CrossOrigin(origins = "*")
@GetMapping("/all/stock/products")
public ResponseEntity<?> getAllStockProducts(@RequestBody ProductGetAllProductsRequest productGetAllProductsRequest) {
    try {
        List<ProductResponse> products = productService.getAllProducts(productGetAllProductsRequest);
        if (products.isEmpty()) {
            return new ResponseEntity<>("No productsController found for the given seller ID", HttpStatus.NOT_FOUND);
        }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>("An internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @CrossOrigin(origins = "*")
    @GetMapping("/get/single/product/")
    public ResponseEntity<?>getSingleProduct(@RequestBody ProductGetByIdsRequest productGetByIdsRequest) {
        try{
            ProductResponse productResponse = productService.getProduct(productGetByIdsRequest);
            if (productResponse == null) {
                return new ResponseEntity<>("No product found for the given ID", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception) {
            return new ResponseEntity<>("An internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

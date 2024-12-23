package org.example.services.goods;

import org.example.data.model.goods.Product;
import org.example.data.repositories.goods.ProductRepository;
import org.example.dto.request.goodsRequest.ProductRequest;
import org.example.dto.response.goodsResponse.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        ProductResponse productResponse = new ProductResponse();
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductPrice(productRequest.getProductPrice());
        product.setProductQuantity(productRequest.getProductQuantity());
        productRepository.save(product);
        productResponse.setMessage(product.getProductName());
        productResponse.setProductId(product.getId());
        return productResponse;
    }


    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        if(productRequest.getProductId() == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }
        ProductResponse productResponse = new ProductResponse();

        Product product =  productRepository.findById(productRequest.getProductId()).
                orElseThrow(()-> new IllegalArgumentException("Product not found"));

                product.setProductName(productRequest.getProductName());
                product.setProductDescription(productRequest.getProductDescription());
                product.setProductPrice(productRequest.getProductPrice());
                product.setProductQuantity(productRequest.getProductQuantity());
                productRepository.save(product);

                productResponse.setProductId(product.getId());
                productResponse.setProductDescription(productRequest.getProductDescription());
                productResponse.setProductQuantity(productRequest.getProductQuantity());
                productResponse.setProductPrice(productRequest.getProductPrice());
                productResponse.setProductName(product.getProductName());
                productResponse.setMessage("updated successfully");


        return productResponse ;
    }

    @Override
    public ProductResponse deleteProduct(ProductRequest productRequest) {
        ProductResponse productResponse = new ProductResponse();
        Optional<Product> optional = productRepository.findById(productRequest.getProductId());
        if (optional.isPresent()) {
            Product product = optional.get();
            if (product.getId().equals(productRequest.getSellerId())) {
                productRepository.delete(product);
                productResponse.setMessage("Product deleted successfully");
            } else {
                productResponse.setMessage("Unauthorized: Product does not belong to the seller");
            }
        }
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts(Long sellerId) {

        List<Product> products = productRepository.findBySellerId(sellerId);

        if (products.isEmpty()) {
            throw new IllegalArgumentException("No products found for the given seller");
        }

        return products.stream()
                .map(product -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setProductId(product.getId());
                    productResponse.setProductName(product.getProductName());
                    productResponse.setProductDescription(product.getProductDescription());
                    productResponse.setProductPrice(product.getProductPrice());
                    productResponse.setProductQuantity(product.getProductQuantity());
                    productResponse.setMessage("Product found");
                    return productResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProduct(Long productId, Long sellerId) {
        ProductResponse productResponse = new ProductResponse();

        if(productId == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }if(sellerId == null) {
            throw new IllegalArgumentException("Seller id must not be null");
        }
        Optional<Product> products = productRepository.findById(productId);
        if(products.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }else{
            Product product = products.get();
            productResponse.setProductId(product.getId());
            productResponse.setProductName(product.getProductName());
            productResponse.setProductDescription(product.getProductDescription());
            productResponse.setProductPrice(product.getProductPrice());
            productResponse.setProductQuantity(product.getProductQuantity());
            productResponse.setMessage("Product found");
        }
        return productResponse;
    }
}

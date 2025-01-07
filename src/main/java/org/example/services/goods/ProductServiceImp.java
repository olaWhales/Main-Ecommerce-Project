package org.example.services.goods;

import org.example.data.model.goods.Product;
import org.example.data.model.user.Seller;
import org.example.data.repositories.goods.ProductRepository;
import org.example.data.repositories.users.SellerRepository;
import org.example.dto.request.goodsRequest.productRequest.*;
import org.example.dto.response.goodsResponse.productResponse.ProductResponse;
import org.example.dto.response.goodsResponse.productResponse.DeleteProductResponse;
import org.example.dto.response.goodsResponse.productResponse.UpdateProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
//    @PersistenceContext
//    private EntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public ProductResponse createProduct( CreateProductRequest createProductRequest) {
        createProductValidation(createProductRequest);

        Seller seller = sellerRepository.findById(createProductRequest.getSellerId()).
                orElseThrow(()-> new IllegalArgumentException("seller not found for seller id " ));

        Product product = new Product();
        product.setProductName(createProductRequest.getProductName());
        product.setProductDescription(createProductRequest.getProductDescription());
        product.setProductPrice(createProductRequest.getProductPrice());
        product.setProductQuantity(createProductRequest.getProductQuantity());
        product.setSeller(seller);
        productRepository.save(product);
        System.out.println("Saving product: " + product);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductName(createProductRequest.getProductName());
        productResponse.setProductDescription(createProductRequest.getProductDescription());
        productResponse.setProductPrice(createProductRequest.getProductPrice());
        productResponse.setProductQuantity(createProductRequest.getProductQuantity());
        productResponse.setMessage("Hi seller, you have successfully created your product");
        productResponse.setProductId(product.getId());
        System.out.println("Product created successfully: " + productResponse);
        return productResponse;
    }


    @Override
    public UpdateProductResponse updateProduct(UpdateProductRequest updateProductRequest) {
        updateProductValidation(updateProductRequest);

            Product product = productRepository.findById(updateProductRequest.getId()).
                orElseThrow(()-> new IllegalArgumentException("Product not found"));
            sellerRepository.findById(updateProductRequest.getSellerId()).
                orElseThrow(()-> new IllegalArgumentException("Seller not found"));

                product.setProductName(updateProductRequest.getProductName());
                product.setProductDescription(updateProductRequest.getProductDescription());
                product.setProductPrice(updateProductRequest.getProductPrice());
                product.setProductQuantity(updateProductRequest.getProductQuantity());
                productRepository.save(product);
                UpdateProductResponse updateProductResponse = new UpdateProductResponse();
                updateProductResponse.setProductName(updateProductRequest.getProductName());
                updateProductResponse.setProductDescription(updateProductRequest.getProductDescription());
                updateProductResponse.setProductPrice(updateProductRequest.getProductPrice());
                updateProductResponse.setProductQuantity(updateProductRequest.getProductQuantity());
                updateProductResponse.setMessage("updated successfully");
                return updateProductResponse;
    }

    @Override
    public DeleteProductResponse deleteProduct(DeleteProductRequest deleteProductRequest) {
        deleteProductValidation(deleteProductRequest);
        Optional<Product> optional = productRepository.findById(deleteProductRequest.getProductId());
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        if (optional.isPresent()) {
            Product product = optional.get();
            if(product.getSeller().getId().equals(deleteProductRequest.getSellerId())) {
                productRepository.delete(product);
                deleteProductResponse.setMessage("Product deleted successfully");
            } else {
                deleteProductResponse.setMessage("Unauthorized: Product does not belong to the seller");
            }
        }
        return deleteProductResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts(ProductGetAllProductsRequest productGetAllProductsRequest) {
        Long sellerId = productGetAllProductsRequest.getSellerId();

        List<Product>products = productRepository.findAllBySellerId(sellerId) ;
        if(products.isEmpty()) {
            throw new IllegalArgumentException("No productsController found");
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
                .toList();
    }

    @Override
    public ProductResponse getProduct(ProductGetByIdsRequest productGetByIdsRequest) {
        Long sellerId = productGetByIdsRequest.getSellerId();
        if(sellerId == null) {
            throw new IllegalArgumentException("Seller id must not be null");
        }

        Long productId = productGetByIdsRequest.getProductId();
        if(productId == null) {
            throw new IllegalArgumentException("Product id must not be null");
        }
        ProductResponse productResponse = new ProductResponse();

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

    public void createProductValidation(CreateProductRequest createProductRequest) {
        if(!sellerRepository.existsById(createProductRequest.getSellerId())) {
            throw new IllegalArgumentException("The given sellerId does not exist");
        }
        if (createProductRequest.getProductPrice() == null || createProductRequest.getProductPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }
        if (createProductRequest.getProductQuantity() == null || createProductRequest.getProductQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        if (createProductRequest.getProductDescription() == null || createProductRequest.getProductDescription().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be empty");
        }
        if (createProductRequest.getProductName() == null || createProductRequest.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
    }


    public void updateProductValidation (UpdateProductRequest updateProductRequest) {
        if(updateProductRequest.getId() == null) {
            throw new IllegalArgumentException("Product id must greater than zero");
        }
    }

    public void deleteProductValidation(DeleteProductRequest deleteProductRequest){
        if(!sellerRepository.existsById(deleteProductRequest.getSellerId())) {
            throw new IllegalArgumentException("The given sellerId does not exist");
        }
        if(!productRepository.existsById(deleteProductRequest.getProductId())) {
            throw new IllegalArgumentException("The given productId does not exist");
        }
    }
}

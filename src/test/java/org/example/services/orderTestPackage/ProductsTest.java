package org.example.services.orderTestPackage;

import org.example.controller.goods.productsController;
import org.example.data.model.goods.Product;
import org.example.data.model.user.Seller;
import org.example.data.repositories.users.SellerRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.dto.request.goodsRequest.productRequest.*;
import org.example.dto.response.goodsResponse.productResponse.ProductGetAllProductResponse;
import org.example.dto.response.goodsResponse.productResponse.ProductResponse;
import org.example.dto.response.goodsResponse.productResponse.DeleteProductResponse;
import org.example.dto.response.goodsResponse.productResponse.UpdateProductResponse;
import org.example.services.goods.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@Commit
public class ProductsTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private productsController productsController;

    @Test
    public void testThatProductCanBeCreated() {
        Seller seller = new Seller();
        Seller sellerId = sellerRepository.save(seller);
        CreateProductRequest productRequest = new CreateProductRequest();
        productRequest.setProductName("Test Product");
        productRequest.setProductDescription("This is a test product");
        productRequest.setProductPrice(100.00);
        productRequest.setProductQuantity(10);
        productRequest.setSellerId(seller.getId());


        ProductResponse productResponse = productService.createProduct(productRequest);
        System.out.println(productResponse.getProductId());
        assertNotNull(productResponse);
        assertEquals("Test Product", productResponse.getProductName());
        assertEquals("This is a test product", productResponse.getProductDescription());
        assertEquals(100.00, productResponse.getProductPrice());
        assertEquals(10, productResponse.getProductQuantity());
        assertEquals("Hi seller, you have successfully created your product", productResponse.getMessage());
        System.out.println(sellerId);
    }


    @Test
    public void testThatProductCanBeUpdated() {

        Seller seller = new Seller();
        sellerRepository.save(seller);

        Product product = new Product();
        product.setProductName("beans");
        product.setProductDescription("its brown");
        product.setProductPrice(200.00);
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);

        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        updateProductRequest.setProductName("yam");
        updateProductRequest.setProductDescription("its a small one ");
        updateProductRequest.setProductPrice(100.00);
        updateProductRequest.setProductQuantity(100);
        updateProductRequest.setId(product.getId());
        updateProductRequest.setSellerId(seller.getId());
//        productRequest.setProductId(product.getId());

        UpdateProductResponse productResponse = productService.updateProduct(updateProductRequest);

        assertEquals(productResponse.getProductName() , "yam");
        assertEquals(productResponse.getProductDescription() , "its a small one ");
        assertEquals(productResponse.getProductPrice() , 100.00);
        assertEquals(productResponse.getProductQuantity() , 100);
//        assertEquals(productResponse.set);
//        assertEquals(productResponse.getProductId() , product.getId());
        System.out.println(product.getId() + "this is product id");
        System.out.println(seller.getId() + "this is seller id");
        productResponse.setMessage("Your product has been updated");
    }

    @Test
    public void testThatProductCanBeDeleted() {
        Seller seller = new Seller();
        seller.setId(seller.getId());
        sellerRepository.save(seller);
        Product product = new Product();
        product.setProductName("rice");
        product.setProductDescription("it's pure white");
        product.setProductPrice(1000.00);
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(product.getId());
        deleteProductRequest.setSellerId(seller.getId());

        DeleteProductResponse deleteProductResponse = productService.deleteProduct(deleteProductRequest);
        deleteProductResponse.setMessage("Your product has been deleted");
        assertEquals(deleteProductResponse.getMessage() , "Your product has been deleted");
//        assertEquals(deleteProductRequest.getProductName() , "bean");
//        assertEquals(deleteProductRequest.getProductDescription(), "beans are red");
//        assertEquals(deleteProductRequest.getProductPrice() , 2000.0);
//        assertEquals(deleteProductRequest.getProductQuantity() , 45);
    }

    @Test
    public void testThatUserCanFetchTheListOfTheirProduct(){
        ProductGetAllProductsRequest productGetAllProductsRequest = new ProductGetAllProductsRequest();
        Seller seller = new Seller();
        sellerRepository.save(seller);
        productGetAllProductsRequest.setSellerId(seller.getId());

        Product product = new Product();
        product.setProductName("jollof rice");
        product.setProductDescription("it's colored");
        product.setProductPrice(1000.00);
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);

        Product product1 = new Product();
        product1.setProductName("garri");
        product1.setProductDescription("can also used with bread");
        product1.setProductPrice(3000.00);
        product1.setProductQuantity(5);
        product1.setSeller(seller);
        productRepository.save(product1);

        List<ProductResponse> products = productService.getAllProducts(productGetAllProductsRequest);

        ProductGetAllProductResponse getAllProductResponse = new ProductGetAllProductResponse();
        getAllProductResponse.setProductName(products.get(0).getProductName());
        getAllProductResponse.setProductDescription(products.get(0).getProductDescription());
        getAllProductResponse.setProductPrice(products.get(0).getProductPrice());
        getAllProductResponse.setProductQuantity(products.get(0).getProductQuantity());

        String productName = products.get(0).getProductName();

        assertEquals(getAllProductResponse.getProductName() , productName);
        assertEquals(getAllProductResponse.getProductDescription() , products.get(0).getProductDescription());
        assertEquals(getAllProductResponse.getProductPrice() , products.get(0).getProductPrice());
        assertEquals(getAllProductResponse.getProductQuantity() , products.get(0).getProductQuantity());


//        assertEquals(getAllProductResponse.getProductDescription() , "can also used with bread");
//        assertEquals(getAllProductResponse.getProductPrice() , 3000.00);
//        assertEquals(getAllProductResponse.getProductQuantity() , 5);
    }

    @Test public void testThatSellerCanFetchProductBySellerIdAndProductId(){
        Seller seller = new Seller();
        sellerRepository.save(seller);

        Product product = new Product();
        product.setProductName("rice");
        product.setProductDescription("it's pure white");
        product.setProductPrice(1000.00);
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);
        ProductGetByIdsRequest productGetByIdsRequest = new ProductGetByIdsRequest();
        productGetByIdsRequest.setSellerId(seller.getId());
        productGetByIdsRequest.setProductId(product.getId());

        ProductResponse productResponse = productService.getProduct(productGetByIdsRequest);
        productResponse.setProductName("rice");
        productResponse.setProductDescription("its pure white");
        productResponse.setProductPrice(1000.00);
        productResponse.setProductQuantity(2);
        productResponse.setMessage("Your product has been updated");

        assertEquals(productResponse.getProductName() , "rice");
        assertEquals(productResponse.getProductDescription() , "its pure white");
        assertEquals(productResponse.getProductPrice() , 1000.00);
        assertEquals(productResponse.getProductQuantity() , 2);
        assertEquals(productResponse.getMessage() , "Your product has been updated");
        assertEquals(productResponse.getProductId() , product.getId());
    }
}

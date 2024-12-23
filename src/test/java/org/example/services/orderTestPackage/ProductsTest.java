package org.example.services.orderTestPackage;

import org.example.data.model.goods.Product;
import org.example.data.model.user.Seller;
import org.example.data.repositories.users.SellerRepository;
import org.example.data.repositories.goods.ProductRepository;
import org.example.dto.request.goodsRequest.ProductRequest;
import org.example.dto.response.goodsResponse.ProductResponse;
import org.example.services.goods.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductsTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void testThatProductCanBeCreated() {
        ProductResponse productResponse = productService.createProduct(new ProductRequest());
        ProductRequest productRequest = new ProductRequest();
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductPrice(productRequest.getProductPrice());
        product.setProductQuantity(productRequest.getProductQuantity());
        productRepository.save(product);
        productResponse.setMessage(productRequest.getProductName());
        assertEquals(productResponse.getMessage(), productRequest.getProductName());
    }

    @Test
    public void testThatProductCanBeUpdated() {

        Seller seller = new Seller();
        sellerRepository.save(seller);

        Product product = new Product();
        product.setProductName("beans");
        product.setProductDescription("its brown");
        product.setProductPrice(BigDecimal.valueOf(200.00));
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("yam");
        productRequest.setProductDescription("its a small one ");
        productRequest.setProductPrice(BigDecimal.valueOf(100.00));
        productRequest.setProductQuantity(100);
        productRequest.setProductId(product.getId());

        ProductResponse productResponse = productService.updateProduct(productRequest);

        assertEquals(productResponse.getProductName() , "yam");
        assertEquals(productResponse.getProductDescription() , "its a small one ");
        assertEquals(productResponse.getProductPrice() , BigDecimal.valueOf(100.00));
        assertEquals(productResponse.getProductQuantity() , 100);
        assertEquals(productResponse.getProductId() , product.getId());
        productResponse.setMessage("Your product has been updated");
    }

    @Test
    public void testThatProductCanBeDeleted() {
        ProductRequest productRequest = new ProductRequest();
        Seller seller = new Seller();
        seller.setId(seller.getId());
        sellerRepository.save(seller);
        Product product = new Product();
        product.setProductName("rice");
        product.setProductDescription("it's pure white");
        product.setProductPrice(BigDecimal.valueOf(1000.00));
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);
        productRequest.setProductId(product.getId());
        productRequest.setProductName("bean");
        productRequest.setProductDescription("beans are red");
        productRequest.setProductPrice(BigDecimal.valueOf(2000.00));
        productRequest.setProductQuantity(45);
        productService.deleteProduct(productRequest);
        Optional<Product> product1 = productRepository.findById(productRequest.getProductId());
        assertTrue(product1.isPresent());
        ProductResponse productResponse = productService.deleteProduct(productRequest);
        productResponse.setMessage("Your product has been deleted");
        assertEquals(productResponse.getMessage() , "Your product has been deleted");
        assertEquals(productRequest.getProductName() , "bean");
        assertEquals(productRequest.getProductDescription(), "beans are red");
        assertEquals(productRequest.getProductPrice() , BigDecimal.valueOf(2000.0));
        assertEquals(productRequest.getProductQuantity() , 45);
    }

    @Test
    public void testThatUserCanFetchTheListOfTheirProduct(){
        Seller seller = new Seller();
        sellerRepository.save(seller);

        Product product = new Product();
        product.setProductName("rice");
        product.setProductDescription("it's pure white");
        product.setProductPrice(BigDecimal.valueOf(1000.00));
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);
        Product product1 = new Product();
        product1.setProductName("beans");
        product1.setProductDescription("beans are red");
        product1.setProductPrice(BigDecimal.valueOf(2000.00));
        product1.setProductQuantity(5);
        product1.setSeller(seller);
        productRepository.save(product1);

        ProductRequest productRequest1 = new ProductRequest();
        productRequest1.setProductName("rice");
        productRequest1.setProductDescription("its pure white ");
        productRequest1.setProductPrice(BigDecimal.valueOf(1000.00));
        productRequest1.setProductQuantity(2);
        productRequest1.setSellerId(seller.getId());

        ProductRequest productRequest2 = new ProductRequest();
        productRequest2.setProductName("beans");
        productRequest2.setProductDescription("beans are red");
        productRequest2.setProductPrice(BigDecimal.valueOf(2000.00));
        productRequest2.setProductQuantity(5);
        productRequest2.setProductId(seller.getId());

        List<ProductResponse> products = productService.getAllProducts(seller.getId());
        assertNotNull(products);

        ProductResponse productResponse = products.get(0);
        ProductResponse productResponse1 = products.get(1);

        assertEquals(productResponse.getProductName() , "rice");
        assertEquals(productResponse1.getProductName(), "beans");
    }

    @Test public void testThatSellerCanFetchProductBySellerIdAndProductId(){
        Seller seller = new Seller();
        sellerRepository.save(seller);

        Product product = new Product();
        product.setProductName("rice");
        product.setProductDescription("it's pure white");
        product.setProductPrice(BigDecimal.valueOf(1000.00));
        product.setProductQuantity(2);
        product.setSeller(seller);
        productRepository.save(product);

        ProductResponse productResponse = productService.getProduct(product.getId(), seller.getId());
        productResponse.setProductName("rice");
        productResponse.setProductDescription("its pure white");
        productResponse.setProductPrice(BigDecimal.valueOf(1000.00));
        productResponse.setProductQuantity(2);
        productResponse.setMessage("Your product has been updated");

        assertEquals(productResponse.getProductName() , "rice");
        assertEquals(productResponse.getProductDescription() , "its pure white");
        assertEquals(productResponse.getProductPrice() , BigDecimal.valueOf(1000.00));
        assertEquals(productResponse.getProductQuantity() , 2);
        assertEquals(productResponse.getMessage() , "Your product has been updated");
        assertEquals(productResponse.getProductId() , product.getId());
    }
}

package delenok.task.service;

import delenok.task.domain.PriceAudit;
import delenok.task.domain.Product;
import delenok.task.repository.ProductRepository;
import delenok.task.service.validator.ProductValidator;
import delenok.task.utils.Utils;
import delenok.task.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PriceAuditService priceAuditService;

    public Product getProduct(String productID) {
        Validator.checkNotNullNotEmpty(productID, "ProductID field is empty");
        Product product = productRepository.findOne(productID);
        ProductValidator.productExist(product);
        log.info("User requested product: {}", product.toString());
        return product;
    }

    public List<Product> getAllProducts() {
        log.info("User requested full list of products");
        return productRepository.findAll();
    }


    public void deleteItem(String productId) {
        Validator.checkNotNullNotEmpty(productId, "ProductID can't be empty");
        Product product = productRepository.findOne(productId);
        ProductValidator.productExist(product);
        productRepository.delete(product);
        log.info("User deleted product: {}", product.toString());
    }

    public Product addNew(Product product) {
        Validator.checkNotNull(product, "Request is null");
        Validator.checkNotNullNotEmpty(product.getName(), "Name can't be empty");
        Validator.checkIfPositive(product.getCurrentPrice(), "Price should be positive");

        product.setTimestamp(Utils.getCurrentTimeInMs());

        productRepository.save(product);
        log.info("New product successfully created: {}", product.toString());
        priceAuditService.addPriceAudit(product);
        return product;
    }

    public Product updatePrice(String productId, BigDecimal price) {
        Validator.checkNotNullNotEmpty(productId, "ProductID can't be empty");
        Validator.checkIfPositive(price, "Price should be positive");
        Product product = productRepository.findOne(productId);
        ProductValidator.productExist(product);
        log.info("User changing price for product with Id {} from {} to {}",
                productId,
                product.getCurrentPrice(),
                price);
        product.setCurrentPrice(price);
        productRepository.save(product);
        log.info("User successfully updated price for product: {}", product.toString());
        priceAuditService.addPriceAudit(product);
        return product;
    }

    public List<PriceAudit> getPricesAudit(String productId) {
        Validator.checkNotNullNotEmpty(productId, "ProductID can't be empty");
        Product product = productRepository.findOne(productId);
        ProductValidator.productExist(product);
        log.info("User requested audit for product with Id: {}", productId);
        return priceAuditService.getPricesAudit(productId);
    }

}

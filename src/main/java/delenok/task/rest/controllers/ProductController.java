package delenok.task.rest.controllers;

import delenok.task.domain.PriceAudit;
import delenok.task.domain.Product;
import delenok.task.rest.RestConst;
import delenok.task.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = RestConst.URL_PRODUCT)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addNewProduct(@Valid @RequestBody Product product) {
        Product productResponse = productService.addNew(product);
        log.info("Controller: New product created: {}; with id {}", productResponse.getName(), productResponse.getId());
        return productResponse;
    }

    @RequestMapping(method = RequestMethod.GET, value = RestConst.URL_PRODUCT_ID)
    @ResponseStatus(value = HttpStatus.OK)
    public Product getProductById(@PathVariable("id") String productId) {
        log.info("Controller: User requested product with ID: {}", productId);
        return productService.getProduct(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = RestConst.URL_PRODUCT)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Product> getProducts() {
        log.info("Controller: User requested products list");
        return productService.getAllProducts();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = RestConst.URL_PRODUCT_ID)
    @ResponseStatus(value = HttpStatus.RESET_CONTENT)
    public void deleteProduct(@PathVariable("id") String productId) {
        log.info("Controller: User trying to delete product with ID: {}", productId);
        productService.deleteItem(productId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = RestConst.URL_PRODUCT_ID)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Product updateProductsPrice(@PathVariable("id") String productId, @RequestParam BigDecimal price) {
        log.info("Controller: User trying to update product price in product with id: {}", productId);
        return productService.updatePrice(productId, price);
    }

    @RequestMapping(method = RequestMethod.GET, value = RestConst.URL_PRICES_AUDIT)
    @ResponseStatus(value = HttpStatus.OK)
    public List<PriceAudit> getPriceAuditForRange(@PathVariable("id") String productId) {
        log.info("Controller: User requested prices audit for product with id: ", productId);
        return productService.getPricesAudit(productId);
    }


}

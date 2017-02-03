package delenok.task.service;

import delenok.task.domain.Product;
import delenok.task.exceptions.ValidationBasicException;
import delenok.task.repository.ProductRepository;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(DataProviderRunner.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class ProductServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private ProductRepository productRepository;
    @Mock
    private PriceAuditService priceAuditService;
    @Spy
    private ProductService productService;

    @DataProvider
    public static Object[] productsForNegativeTest() {
        return new Object[]{
                null,
                getProductRequest("", BigDecimal.valueOf(-100), 100L),
                getProductRequest(null, BigDecimal.valueOf(-100), 100L),
                getProductRequest("", null, 100L),
                getProductRequest("", null, null),
        };
    }

    private static Product getProductRequest(String name, BigDecimal currentPrice, Long timestamp) {
        Product product = new Product();
        product.setName(name);
        product.setCurrentPrice(currentPrice);
        product.setTimestamp(timestamp);
        return product;
    }

    private static Product getPredefinedProduct() {
        return Product.builder()
                .name("Test_Product")
                .currentPrice(BigDecimal.valueOf(1000))
                .timestamp(100000000L)
                .build();
    }

    private static List<Product> getPredefinedListOfProducts() {
        List<Product> mockedCreatorsList = new ArrayList<>();
        mockedCreatorsList.add(getPredefinedProduct());
        mockedCreatorsList.add(getPredefinedProduct());
        mockedCreatorsList.add(getPredefinedProduct());
        return mockedCreatorsList;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService();
        productService.priceAuditService = priceAuditService;
        productService.productRepository = productRepository;
    }

    @Test
    public void successProductAdding() {
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(getPredefinedProduct());
        Mockito.doNothing().when(priceAuditService).addPriceAudit(any(Product.class));
        Product productResponse = productService.addNew(getPredefinedCreator());
        assertEquals(productResponse.getName(), "Test_Request");
        assertEquals(productResponse.getCurrentPrice(), BigDecimal.valueOf(10001));
    }

    @Test
    @UseDataProvider("productsForNegativeTest")
    public void negativeCreatorAddingWithDataProviders(Product product) throws Exception {
        thrown.expect(ValidationBasicException.class);
        productService.addNew(product);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void successProductList() {
        Mockito.when(productRepository.findAll()).thenReturn(getPredefinedListOfProducts());
        List<Product> defaultCreatorsList = productService.getAllProducts();
        verify(productRepository).findAll();
    }

    @Test
    public void successDeleteProduct() {
        Mockito.when(productRepository.findOne(any(String.class))).thenReturn(getPredefinedProduct());
        String id = "Bla Bla";
        productService.deleteItem(id);
        verify(productRepository).delete(any(Product.class));
    }

    @Test
    public void negativeDeleteCreator() {
        thrown.expect(ValidationBasicException.class);
        productService.deleteItem(null);
        verify(productRepository, never()).save(any(Product.class));
    }

    private Product getPredefinedCreator() {
        Product productRequest = new Product();
        productRequest.setName("Test_Request");
        productRequest.setCurrentPrice(BigDecimal.valueOf(10001));
        productRequest.setTimestamp(100000000L);
        return productRequest;
    }
}

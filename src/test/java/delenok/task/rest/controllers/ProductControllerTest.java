package delenok.task.rest.controllers;

import delenok.task.domain.PriceAudit;
import delenok.task.domain.Product;
import delenok.task.rest.controllers.ProductController;
import delenok.task.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private final static Long TIMESTAMP = 1000L;
    private final static BigDecimal PRICE_1 = BigDecimal.valueOf(1000.1);
    private final static BigDecimal PRICE_2 = BigDecimal.valueOf(2000.1);

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }

    @Test
    public void get_all_products_success() throws Exception {
        List<Product> products = Arrays.asList(
                new Product("some", PRICE_1, TIMESTAMP),
                new Product("thing", PRICE_2, TIMESTAMP));
        when(productService.getAllProducts()).thenReturn(products);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].currentPrice", is(PRICE_1.doubleValue())))
                .andExpect(jsonPath("$[0].name", is("some")))
                .andExpect(jsonPath("$[1].currentPrice", is(PRICE_2.doubleValue())))
                .andExpect(jsonPath("$[1].name", is("thing")));
        verify(productService, times(1)).getAllProducts();
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void get_all_prices_for_products_success() throws Exception {
        List<PriceAudit> priceAudits = Arrays.asList(
                new PriceAudit("id_1", "name", PRICE_1, TIMESTAMP),
                new PriceAudit("id_2", "name_2", PRICE_2, TIMESTAMP));
        when(productService.getPricesAudit("id")).thenReturn(priceAudits);
        mockMvc.perform(get("/products/{id}/prices", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].price", is(PRICE_1.doubleValue())))
                .andExpect(jsonPath("$[0].productName", is("name")))
                .andExpect(jsonPath("$[0].productId", is("id_1")))
                .andExpect(jsonPath("$[1].price", is(PRICE_2.doubleValue())))
                .andExpect(jsonPath("$[1].productName", is("name_2")))
                .andExpect(jsonPath("$[1].productId", is("id_2")));
        verify(productService, times(1)).getPricesAudit("id");
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void get_product_by_id_success() throws Exception {
        Product product = new Product("test", PRICE_1, TIMESTAMP);
        when(productService.getProduct("id")).thenReturn(product);
        mockMvc.perform(get("/products/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.currentPrice", is(PRICE_1.doubleValue())))
                .andExpect(jsonPath("$.name", is("test")));

        verify(productService, times(1)).getProduct("id");
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void create_new_product_success() throws Exception {
        Product product = new Product("test", PRICE_1, TIMESTAMP);
        when(productService.addNew(any())).thenReturn(product);
        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(asJsonString(product)))
                .andExpect(status().isCreated());

        verify(productService, times(1)).addNew(product);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void delete_product_success() throws Exception {
        doNothing().when(productService).deleteItem(any());
        mockMvc.perform(
                delete("/products/{id}", "id")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isResetContent());

        verify(productService, times(1)).deleteItem("id");
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void update_product_price_success() throws Exception {
        Product product = new Product("test", PRICE_1, TIMESTAMP);
        when(productService.updatePrice("id", PRICE_1)).thenReturn(product);
        mockMvc.perform(
                put("/products/{id}", "id")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("price", PRICE_1.toString()))
                .andExpect(status().isAccepted());

        verify(productService, times(1)).updatePrice("id", PRICE_1);
        verifyNoMoreInteractions(productService);
    }
    @Test
    public void update_product_price_fail() throws Exception {
        mockMvc.perform(
                put("/products/{id}", "id")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("price", "test"))
                .andExpect(status().isBadRequest());
        verifyZeroInteractions(productService);
    }


}

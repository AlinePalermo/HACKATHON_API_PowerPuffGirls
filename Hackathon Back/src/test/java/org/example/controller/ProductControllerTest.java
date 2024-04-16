package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Sql(scripts = "/db/inject_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldReturnAsExpectedWhenFindById() throws Exception {
        String URL = "/api/product/{id}";
        String response = mockMvc.perform(get(URL, 1))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO productDTO = objectMapper.readValue(response, ProductDTO.class);
        assertNotNull(productDTO);
        assertEquals(1L, productDTO.getId());
        assertEquals("Product A", productDTO.getName());
        assertEquals(10, productDTO.getStockItems());
        assertEquals(99.99d, productDTO.getPrice());
    }

    @Sql(scripts = "/db/inject_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldReturnEmptyObjectWhenNotFindById() throws Exception {
        String URL = "/api/product/{id}";
        String response = mockMvc.perform(get(URL, 10))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO productDTO = objectMapper.readValue(response, ProductDTO.class);
        assertNotNull(productDTO);
        assertEquals(0L, productDTO.getId());
        assertNull(productDTO.getName());
        assertNull(productDTO.getStockItems());
        assertNull(productDTO.getPrice());
    }

    @Sql(scripts = "/db/inject_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldReturnAllAsExpected() throws Exception {
        String URL = "/api/product/all";
        String response = mockMvc.perform(get(URL))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ProductDTO> productDTOList = objectMapper.readValue(response, new TypeReference<>() {
        });

        assertFalse(productDTOList.isEmpty());
        assertEquals(3, productDTOList.size());
        assertEquals(1L, productDTOList.get(0).getId());
        assertEquals("Product A", productDTOList.get(0).getName());
        assertEquals(10, productDTOList.get(0).getStockItems());
        assertEquals(99.99d, productDTOList.get(0).getPrice());
    }

    @Test
    void shouldReturnCreateAsExpected() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("New Product");
        productDTO.setStockItems(5);
        productDTO.setPrice(49.99);

        mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
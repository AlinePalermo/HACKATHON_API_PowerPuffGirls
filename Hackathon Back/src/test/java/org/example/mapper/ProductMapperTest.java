package org.example.mapper;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.service.ProductServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Test
    public void shouldConvertEntityToDTOWithNonNullProduct() {
        Product product = ProductServiceTest.createProduct(1L, "Test Product",100);
        product.setStockItems(5);

        ProductDTO productDTO = productMapper.convertEntityToDTO(product);

        assertNotNull(productDTO);
        assertEquals(1L, productDTO.getId());
        assertEquals("Test Product", productDTO.getName());
        assertEquals(100.0, productDTO.getPrice(), 0.001);
        assertEquals(5, productDTO.getStockItems());
    }

    @Test
    public void shouldConvertEntityToDTOWithNullProduct() {
        ProductDTO productDTO = productMapper.convertEntityToDTO(null);

        assertNotNull(productDTO);
        assertEquals(0L, productDTO.getId());
        assertNull(productDTO.getName());
        assertNull(productDTO.getPrice());
        assertNull(productDTO.getPrice());
    }

    @Test
    void shouldConvertDTOToEntityWithNonNullProduct() {
        ProductDTO productDTO = ProductServiceTest.createProductDTO(1L, "Test Product",100);
        productDTO.setStockItems(5);

        Product product = productMapper.convertDTOToEntity(productDTO);

        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(100.0, product.getPrice(), 0.001);
        assertEquals(5, product.getStockItems());
    }
}

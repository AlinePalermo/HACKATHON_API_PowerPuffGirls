package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productMapper = mock(ProductMapper.class);
        productService = new ProductService(productRepository, productMapper);
    }
    @Test
    void shouldCreateProduct() {
        ProductDTO inputDTO = createProductDTO(1, "Test Product", 50.0);
        Product mockProduct = createProduct(1, "Test Product", 50.0);

        when(productMapper.convertDTOToEntity(inputDTO)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        Long productId = productService.createProduct(inputDTO);

        verify(productMapper, times(1)).convertDTOToEntity(inputDTO);
        verify(productRepository, times(1)).save(mockProduct);

        assertNotNull(productId);
        assertEquals(mockProduct.getId(), productId);
    }

    @Test
    void shouldGetProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct(1L, "Product 1", 50.0));
        productList.add(createProduct(2L, "Product 2", 75.0));

        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(createProductDTO(1L, "Product 1", 50.0));
        productDTOList.add(createProductDTO(2L, "Product 2", 75.0));

        when(productRepository.findAll()).thenReturn(productList);

        for (Product product : productList) {
            when(productMapper.convertEntityToDTO(product))
                    .thenReturn(createProductDTO(product.getId(), product.getName(), product.getPrice()));
        }


        List<ProductDTO> result = productService.getProducts();

        verify(productRepository, times(1)).findAll();

        for (Product product : productList) {
            verify(productMapper, times(1)).convertEntityToDTO(product);
        }

        assertNotNull(result);
        assertEquals(productDTOList.size(), result.size());
        assertEquals(productDTOList, result);
    }

    /** Refactor this part to a HelperFactory Class **/
    public static ProductDTO createProductDTO(long id, String name, double price) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setPrice(price);

        return productDTO;
    }

    public static Product createProduct(long id, String name, double price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);

        return product;
    }

}
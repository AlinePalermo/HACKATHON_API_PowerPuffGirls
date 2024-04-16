package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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
    void shouldReturnAsExpectedWhenGetById() {
        long productId = 1L;
        Product product = createProduct(1L, "Test Product", 100);
        ProductDTO productDTO = createProductDTO(1L, "Test Product", 100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.convertEntityToDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).convertEntityToDTO(product);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(100, result.getPrice());
    }

    @Test
    public void shouldReturnNullWhenIdNotFound() {
        long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        ProductDTO result = productService.getProductById(productId);
        verify(productRepository, times(1)).findById(productId);

        assertNull(result);
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

    @Test
    void shouldCreateProduct() {
        ProductDTO inputDTO = new ProductDTO();
        inputDTO.setName("Test Product");
        inputDTO.setPrice(50.0);

        Product mockProduct = new Product();
        mockProduct.setId(1L);

        when(productMapper.convertDTOToEntity(inputDTO)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        Long productId = productService.createProduct(inputDTO);

        verify(productMapper, times(1)).convertDTOToEntity(inputDTO);
        verify(productRepository, times(1)).save(mockProduct);

        assertNotNull(productId);
        assertEquals(mockProduct.getId(), productId);
    }

    @Test
    public void testUpdateProductDTO_UpdatesTargetWithSourceValues() {
        ProductDTO source = createProductDTO(1L, "Test Product", 100);
        source.setStockItems(10);

        ProductDTO target = createProductDTO(2L, "Existing Product", 50);
        target.setStockItems(5);

        productService.updateFrom(source, target);

        assertEquals(source.getId(), target.getId());
        assertEquals(source.getName(), target.getName());
        assertEquals(source.getPrice(), target.getPrice());
        assertEquals(source.getStockItems(), target.getStockItems());
    }

    @Test
    void updateProduct() {
        long productId = 1L;

        ProductDTO sourceProductDTO = createProductDTO(2L, "Updated Product", 75.0);
        ProductDTO targetProductDTO = createProductDTO(productId, "Existing Product", 50.0);
        Product product = createProduct(productId, "Existing Product", 50.0);

        when(productService.getProductById(productId)).thenReturn(targetProductDTO);
        when(productMapper.convertDTOToEntity(targetProductDTO)).thenReturn(product);

        Long updatedProductId = productService.updateProduct(productId, sourceProductDTO);

        assertNotNull(updatedProductId);
        assertEquals(productId, updatedProductId);
    }

    @Test
    public void testDeleteProduct_ProductFound() {
        long productId = 1L;
        Product product = createProduct(1L, "Test product", 100);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productService.deleteProduct(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
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

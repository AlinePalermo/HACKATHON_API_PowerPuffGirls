package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id).orElse(null);
        return productMapper.convertEntityToDTO(product);
    }

    public List<ProductDTO> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList) {
            ProductDTO productDTO = productMapper.convertEntityToDTO(product);
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    public Long createProduct(ProductDTO productDTO) {
        Product product = productMapper.convertDTOToEntity(productDTO);
        productRepository.save(product);
        return product.getId();
    }

    public Long updateProduct(long id, ProductDTO sourceProductDTO) {
        ProductDTO targetProductDTO = getProductById(id);
        updateFrom(sourceProductDTO, targetProductDTO);
        Product product = productMapper.convertDTOToEntity(targetProductDTO);
        productRepository.save(product);
        return product.getId();
    }

    void updateFrom(ProductDTO source, ProductDTO target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setStockItems(source.getStockItems());
    }

    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
    }
}

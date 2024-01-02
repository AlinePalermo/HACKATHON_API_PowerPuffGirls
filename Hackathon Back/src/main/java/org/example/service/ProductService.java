package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id).orElse(null);
        return convertEntityToDTO(product);
    }

    public List<ProductDTO> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList) {
            ProductDTO productDTO = convertEntityToDTO(product);
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    public Long createProduct(ProductDTO productDTO) {
        Product product = convertDTOToEntity(productDTO);
        productRepository.save(product);
        return product.getId();
    }

    public Long updateProduct(long id, ProductDTO sourceProductDTO) {
        ProductDTO targetProductDTO = getProductById(id);
        updateFrom(sourceProductDTO, targetProductDTO);
        Product product = convertDTOToEntity(targetProductDTO);
        productRepository.save(product);
        return product.getId();
    }

    private void updateFrom(ProductDTO source, ProductDTO target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setStockItems(source.getStockItems());
    }

    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
    }

    public ProductDTO convertEntityToDTO(Product product) {
        if(Objects.isNull(product)) {
            return new ProductDTO();
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setStockItems(product.getStockItems());

        return productDTO;
    }

    public Product convertDTOToEntity(ProductDTO productDTO) {
        if(Objects.isNull(productDTO)) {
            return new Product();
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStockItems(productDTO.getStockItems());

        return product;
    }
}

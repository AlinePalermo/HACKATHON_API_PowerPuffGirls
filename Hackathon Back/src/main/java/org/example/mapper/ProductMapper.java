package org.example.mapper;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper {

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

package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductFileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductFileService {

    private final ProductFileRepository productFileRepository;
    private final ProductMapper productMapper;

    public ProductFileService(ProductFileRepository productFileRepository, ProductMapper productMapper) {
        this.productFileRepository = productFileRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO getFileProductById(long id) {
        Optional<Product> optionalProduct = productFileRepository.findById(id);

        return optionalProduct.map(productMapper::convertEntityToDTO).orElse(null);
    }

    public List<ProductDTO> getFileProducts() {
        List<Product> productList = productFileRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product : productList) {
            ProductDTO productDTO = productMapper.convertEntityToDTO(product);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    public Long createFileProduct(ProductDTO productDTO) {
        Product product = productMapper.convertDTOToEntity(productDTO);
        productFileRepository.saveFile(product);
        return product.getId();
    }

    public Long updateFileProduct(long id, ProductDTO sourceproductDTO) {
        ProductDTO targetProductDTO = getFileProductById(id);
        updateFileFrom(sourceproductDTO, targetProductDTO);
        Product product = productMapper.convertDTOToEntity(targetProductDTO);
        productFileRepository.saveFile(product);
        return product.getId();
    }

    private void updateFileFrom(ProductDTO source, ProductDTO target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setStockItems(source.getStockItems());
    }

    public void deleteFileProduct(long id) {
        productFileRepository.deleteById(id);
    }
}

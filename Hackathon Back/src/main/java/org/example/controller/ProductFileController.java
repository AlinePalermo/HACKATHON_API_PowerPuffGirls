package org.example.controller;

import org.example.dto.ProductDTO;
import org.example.service.ProductFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product/file")
public class ProductFileController {

    @Autowired
    private ProductFileService productFileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getFileProductById(@PathVariable long id) {
        return new ResponseEntity<>(productFileService.getFileProductById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getFileProducts() {
        return new ResponseEntity<>(productFileService.getFileProducts(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createFileProduct(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productFileService.createFileProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateFileProduct(@PathVariable long id, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productFileService.updateFileProduct(id, productDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFileProduct(@PathVariable long id) {
        productFileService.deleteFileProduct(id);
    }
}

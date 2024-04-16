package org.example.repository;

import org.example.entity.Product;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductFileRepository {
    private static final String FILE_PATH = "src/main/resources/db_file/products.txt";
    private static File file;

    public ProductFileRepository() {
        file = Objects.isNull(file) ? new File(FILE_PATH) : file;
    }

    public Optional<Product> findById(long id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Long productId = Long.parseLong(parts[0]);
                if (productId.equals(id)) {
                    Product product = new Product();
                    product.setId(productId);
                    product.setName(parts[1]);
                    product.setStockItems(Integer.valueOf(parts[2]));
                    product.setPrice(Double.parseDouble(parts[3]));
                    return Optional.of(product);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Product product = new Product();
                product.setName(parts[1]);
                product.setStockItems(Integer.valueOf(parts[2]));
                product.setPrice(Double.parseDouble(parts[3]));
                productList.add(product);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void saveFile(Product product) {
        List<Product> productList = findAll();
        long id = 1;

        if (!productList.isEmpty()) {
            long lastId = productList.get(productList.size() - 1).getId();
            id = lastId + 1;
        }

        product.setId(id);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(product.getId() + "," + product.getName() + "," + product.getStockItems() + "," + product.getPrice());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveList(List<Product> productList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : productList) {
                bw.write(product.getId() + "," + product.getName() + "," + product.getStockItems() + "," + product.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(long id) {
        List<Product> productList = findAll();
        productList.removeIf(product -> product.getId() == id);
        saveList(productList);
    }


}

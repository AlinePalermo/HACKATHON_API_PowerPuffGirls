package org.example.dto;

import java.util.Objects;

public class ProductDTO {

    private long id;
    private String name;
    private Integer stockItems;
    private Double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStockItems() {
        return stockItems;
    }

    public void setStockItems(Integer stockItems) {
        this.stockItems = stockItems;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(stockItems, that.stockItems) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stockItems, price);
    }
}

package org.example.dto;

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
}

package org.example.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = -7703612168273271525L;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProductDTO that = (ProductDTO) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

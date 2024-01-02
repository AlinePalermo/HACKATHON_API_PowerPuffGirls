package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "item_name")
    private String name;
    @Column(name = "items_in_stock")
    private Integer stockItems;
    @Column(name = "price")
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

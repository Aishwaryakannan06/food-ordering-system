package com.college.foodordering.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "food_items")
public class FoodItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    @NotBlank private String category;
    @NotBlank private String description;
    @NotNull @DecimalMin("0.0") @Column(precision = 10, scale = 2)
    private BigDecimal price;
    @NotBlank private String emoji;
    private boolean available = true;

    public FoodItem() {}
    public FoodItem(String name, String category, String description, BigDecimal price, String emoji) {
        this.name=name; this.category=category; this.description=description; this.price=price; this.emoji=emoji; this.available=true;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getCategory(){return category;} public void setCategory(String category){this.category=category;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal price){this.price=price;}
    public String getEmoji(){return emoji;} public void setEmoji(String emoji){this.emoji=emoji;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean available){this.available=available;}
}

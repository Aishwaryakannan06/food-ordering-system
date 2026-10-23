package com.college.foodordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="food_item_id")
    private FoodItem foodItem;
    private int quantity;
    private String orderedBy;
    @Column(precision=10, scale=2) private BigDecimal unitPrice;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id")
    @JsonIgnore private CustomerOrder order;

    public OrderLine() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public FoodItem getFoodItem(){return foodItem;} public void setFoodItem(FoodItem foodItem){this.foodItem=foodItem;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public String getOrderedBy(){return orderedBy;} public void setOrderedBy(String orderedBy){this.orderedBy=orderedBy;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal unitPrice){this.unitPrice=unitPrice;}
    public CustomerOrder getOrder(){return order;} public void setOrder(CustomerOrder order){this.order=order;}
}

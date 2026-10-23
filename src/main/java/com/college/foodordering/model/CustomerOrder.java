package com.college.foodordering.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;
    private String status = "PLACED";
    @Column(precision=10, scale=2) private BigDecimal subtotal;
    @Column(precision=10, scale=2) private BigDecimal tax;
    @Column(precision=10, scale=2) private BigDecimal total;
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<OrderLine> items = new ArrayList<>();

    public CustomerOrder() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getGroupName(){return groupName;} public void setGroupName(String groupName){this.groupName=groupName;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
    public BigDecimal getSubtotal(){return subtotal;} public void setSubtotal(BigDecimal subtotal){this.subtotal=subtotal;}
    public BigDecimal getTax(){return tax;} public void setTax(BigDecimal tax){this.tax=tax;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal total){this.total=total;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
    public List<OrderLine> getItems(){return items;} public void setItems(List<OrderLine> items){this.items=items;}
}

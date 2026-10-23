package com.college.foodordering.controller;

import com.college.foodordering.dto.OrderRequest;
import com.college.foodordering.model.*;
import com.college.foodordering.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    private final CustomerOrderRepository orders;
    private final FoodItemRepository foods;
    public OrderController(CustomerOrderRepository orders, FoodItemRepository foods){this.orders=orders;this.foods=foods;}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public CustomerOrder place(@Valid @RequestBody OrderRequest request) {
        CustomerOrder order = new CustomerOrder();
        order.setGroupName(request.groupName().trim());
        BigDecimal subtotal = BigDecimal.ZERO;
        for (OrderRequest.ItemRequest item : request.items()) {
            FoodItem food = foods.findById(item.foodItemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown food ID: " + item.foodItemId()));
            if (!food.isAvailable()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, food.getName()+" is unavailable");
            OrderLine line = new OrderLine();
            line.setFoodItem(food); line.setQuantity(item.quantity()); line.setOrderedBy(item.orderedBy().trim());
            line.setUnitPrice(food.getPrice()); line.setOrder(order); order.getItems().add(line);
            subtotal = subtotal.add(food.getPrice().multiply(BigDecimal.valueOf(item.quantity())));
        }
        BigDecimal tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
        order.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        order.setTax(tax); order.setTotal(subtotal.add(tax).setScale(2, RoundingMode.HALF_UP));
        return orders.save(order);
    }

    @GetMapping
    public List<CustomerOrder> all(){return orders.findAll();}

    @GetMapping("/{id}")
    public CustomerOrder one(@PathVariable Long id){
        return orders.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PatchMapping("/{id}/status")
    public CustomerOrder updateStatus(@PathVariable Long id, @RequestParam String status) {
        List<String> allowed = List.of("PLACED", "PREPARING", "READY", "COMPLETED", "CANCELLED");
        if(!allowed.contains(status.toUpperCase())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        CustomerOrder order = orders.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        order.setStatus(status.toUpperCase()); return orders.save(order);
    }
}

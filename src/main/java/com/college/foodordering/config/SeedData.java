package com.college.foodordering.config;

import com.college.foodordering.model.FoodItem;
import com.college.foodordering.repository.FoodItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class SeedData {
    @Bean
    CommandLineRunner seedFoods(FoodItemRepository foods) {
        return args -> {
            if (foods.count() > 0) return;
            foods.save(new FoodItem("Burger","Meals","Classic comfort, freshly made",new BigDecimal("100.00"),"🍔"));
            foods.save(new FoodItem("Pizza","Meals","Cheesy favourite for sharing",new BigDecimal("200.00"),"🍕"));
            foods.save(new FoodItem("Sandwich","Snacks","A light, tasty bite",new BigDecimal("80.00"),"🥪"));
            foods.save(new FoodItem("French Fries","Snacks","Golden and crispy",new BigDecimal("60.00"),"🍟"));
            foods.save(new FoodItem("Noodles","Meals","Warm, savoury and satisfying",new BigDecimal("120.00"),"🍜"));
            foods.save(new FoodItem("Juice","Drinks","A refreshing sip",new BigDecimal("50.00"),"🧃"));
        };
    }
}

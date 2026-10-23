package com.college.foodordering.controller;

import com.college.foodordering.model.FoodItem;
import com.college.foodordering.repository.FoodItemRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodController {
    private final FoodItemRepository foods;
    public FoodController(FoodItemRepository foods){this.foods=foods;}

    @GetMapping
    public List<FoodItem> all(@RequestParam(required=false) String category,
                              @RequestParam(required=false) String search) {
        List<FoodItem> list = (category == null || category.isBlank())
            ? foods.findByAvailableTrueOrderByCategoryAscNameAsc()
            : foods.findByCategoryIgnoreCaseAndAvailableTrue(category);
        if (search == null || search.isBlank()) return list;
        String term = search.toLowerCase();
        return list.stream().filter(f -> f.getName().toLowerCase().contains(term)
            || f.getDescription().toLowerCase().contains(term)).toList();
    }

    @GetMapping("/{id}")
    public FoodItem one(@PathVariable Long id) {
        return foods.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food item not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodItem create(@Valid @RequestBody FoodItem item){ item.setId(null); return foods.save(item); }

    @PutMapping("/{id}")
    public FoodItem update(@PathVariable Long id, @Valid @RequestBody FoodItem input) {
        FoodItem item = foods.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food item not found"));
        item.setName(input.getName()); item.setCategory(input.getCategory()); item.setDescription(input.getDescription());
        item.setPrice(input.getPrice()); item.setEmoji(input.getEmoji()); item.setAvailable(input.isAvailable());
        return foods.save(item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){ if(!foods.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food item not found"); foods.deleteById(id); }
}

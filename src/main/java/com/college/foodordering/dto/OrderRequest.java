package com.college.foodordering.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record OrderRequest(
    @NotBlank @Size(max=80) String groupName,
    @NotEmpty List<@Valid ItemRequest> items
) {
    public record ItemRequest(
        @NotNull @Positive Long foodItemId,
        @Min(1) @Max(99) int quantity,
        @NotBlank @Size(max=80) String orderedBy
    ) {}
}

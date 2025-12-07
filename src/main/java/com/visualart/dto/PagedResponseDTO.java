package com.visualart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Paginated response wrapper")
public record PagedResponseDTO<T>(
        @Schema(description = "List of items in the current page")
        List<T> list,

        @Schema(description = "Total number of pages available", example = "5")
        int totalPages,

        @Schema(description = "Total number of items across all pages", example = "42")
        long totalItems
) {}

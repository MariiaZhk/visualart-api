package com.visualart.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

// Generic DTO для списку з пагінацією
@Data
@AllArgsConstructor
public class PagedResponseDTO<T> {
    private List<T> items;
    private int totalPages;
    private long totalItems; // опціонально, якщо потрібна кількість записів
}

package com.visualart.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
//DTO
@Data
@Builder
public class PagedResponseDTO<T> {
    private List<T> items;
    private int totalPages;
    private long totalItems;
}

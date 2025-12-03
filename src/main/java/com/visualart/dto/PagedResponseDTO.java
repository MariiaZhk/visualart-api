package com.visualart.dto;

import java.util.List;

public record PagedResponseDTO<T>(
        List<T> items,
        int totalPages,
        long totalItems
) {}

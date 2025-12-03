package com.visualart.dto;

public record UploadResponseDTO(
        int successCount,
        int failedCount
) {}

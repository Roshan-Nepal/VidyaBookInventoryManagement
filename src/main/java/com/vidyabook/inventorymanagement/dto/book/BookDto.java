package com.vidyabook.inventorymanagement.dto.book;

import java.util.Date;

public record BookDto(
        Long id,
        String title,
        String author,
        Date publishedDate,
        Double price,
        Long quantity,
        int minStockLevel,
        String description

) {
}

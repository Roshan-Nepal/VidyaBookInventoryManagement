package com.vidyabook.inventorymanagement.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Double price;
    private Long quantity;
    private LocalDateTime publishedDate;
}

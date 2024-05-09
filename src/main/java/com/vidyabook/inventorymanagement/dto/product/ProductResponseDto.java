package com.vidyabook.inventorymanagement.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Double price;
    private Long quantity;
    private Date publishedDate;
}

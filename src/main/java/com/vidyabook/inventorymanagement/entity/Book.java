package com.vidyabook.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "bookId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", unique = true,nullable = false)
    private String title;

    @Column(name = "author", unique = false, nullable = false)
    private String author;

    @Column(name = "description", unique = false, nullable = true)
    private String description;

    @Column(name = "price", unique = false, nullable = false)
    private Double price;

    @Column(name = "quantity",nullable = false)
    private Long quantity;

    @Column(name = "published_date",nullable = true)
    private Date publishedDate;
}

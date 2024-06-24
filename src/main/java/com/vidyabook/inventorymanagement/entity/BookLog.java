package com.vidyabook.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_log")
public class BookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long logId;

    @Column(name = "book_name")
    private String book;


    private String user;
    @Column(name = "action")

    String action;
    @Column(name = "performed_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime performedTime = LocalDateTime.now();
}

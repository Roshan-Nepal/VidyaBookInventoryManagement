package com.vidyabook.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier")
public class Suppliers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long supplierId;

    @Column(name = "supplier_name", nullable = false, unique = true)
    private String supplierName;


    @Column(name = "supplier_email", nullable = false, unique = true)
    private String supplierEmail;

    @Column(name = "supplier_contact_number", nullable = false, unique = true)
    private String supplierContactNumber;

}

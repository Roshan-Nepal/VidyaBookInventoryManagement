package com.vidyabook.inventorymanagement.repository;

import com.vidyabook.inventorymanagement.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Suppliers,Long> {
    Optional<Suppliers> findSuppliersBySupplierName(String supplierName);
}

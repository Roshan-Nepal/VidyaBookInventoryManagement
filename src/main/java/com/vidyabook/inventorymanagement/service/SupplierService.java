package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.entity.Suppliers;

import java.util.List;

public interface SupplierService {
    boolean updateSupplier(Long supplierId, Suppliers suppliers);
    boolean deleteSupplier(Long supplierId);


    List<Suppliers> getSuppliers();

    boolean addSupplier(Suppliers suppliers);
    Suppliers getSupplier(Long id);
}

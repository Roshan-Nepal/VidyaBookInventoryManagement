package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.entity.Book;
import com.vidyabook.inventorymanagement.entity.BookLog;
import com.vidyabook.inventorymanagement.entity.Suppliers;
import com.vidyabook.inventorymanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SupplierServiceImplementation implements SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierServiceImplementation(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Suppliers> getSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public boolean updateSupplier(Long supplierId, Suppliers suppliers) {
        Suppliers supplierToUpdate = supplierRepository.getReferenceById(supplierId);
        if(supplierToUpdate.getSupplierName().equals(suppliers.getSupplierName()) && supplierToUpdate.getSupplierEmail().equals(suppliers.getSupplierEmail())
        && Objects.equals(supplierToUpdate.getSupplierContactNumber(),suppliers.getSupplierContactNumber())){
            return false;
        }
        supplierToUpdate.setSupplierName(suppliers.getSupplierName());
        supplierToUpdate.setSupplierEmail(suppliers.getSupplierEmail());
        supplierToUpdate.setSupplierContactNumber(suppliers.getSupplierContactNumber());
        Suppliers updatedSupplier = supplierRepository.save(supplierToUpdate);
        return Objects.equals(updatedSupplier.getSupplierId(), suppliers.getSupplierId());
    }

    @Override
    public boolean deleteSupplier(Long supplierId) {
        supplierRepository.deleteById(supplierId);
        Optional<Suppliers> suppliers = supplierRepository.findById(supplierId);
        return suppliers.isEmpty();

    }

    @Override
    public boolean addSupplier(Suppliers suppliers) {

        try {
            if (supplierRepository.findSuppliersBySupplierName(suppliers.getSupplierName()).isPresent()) {
                return false;
            }
            Suppliers addedSupplier = supplierRepository.save(suppliers);
            return supplierRepository.findById(addedSupplier.getSupplierId()).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Suppliers getSupplier(Long id) {
        if (supplierRepository.findById(id).isPresent()) {
            return supplierRepository.getReferenceById(id);
        }
        return new Suppliers();
    }
}

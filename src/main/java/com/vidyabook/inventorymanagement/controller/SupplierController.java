package com.vidyabook.inventorymanagement.controller;

import com.vidyabook.inventorymanagement.entity.Suppliers;
import com.vidyabook.inventorymanagement.service.SupplierServiceImplementation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.vidyabook.inventorymanagement.controller.BookController.checkSession;

@Controller
@RequestMapping("/home")
public class SupplierController {
    private final SupplierServiceImplementation supplierServiceImplementation;

    public SupplierController(SupplierServiceImplementation supplierServiceImplementation) {
        this.supplierServiceImplementation = supplierServiceImplementation;
    }

    @GetMapping("/suppliers")
    public String getAllSupplier(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        List<Suppliers> suppliers = supplierServiceImplementation.getSuppliers();
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("supplier", new Suppliers());
        model.addAttribute("cPage", "supplier");
        return "supplier";
    }

    @GetMapping("/addsupplier")
    public String addSupplier(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("cPage", "supplier");
        model.addAttribute("supplier", new Suppliers());
        return "addsupplier";
    }

    @PostMapping("/addsupplier")
    public String addSupplier(@ModelAttribute Suppliers supplier, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        boolean addSuccess = supplierServiceImplementation.addSupplier(supplier);
        if (!addSuccess) {
            redirectAttributes.addFlashAttribute("addSupplierFailed", true);
            return "redirect:/home/suppliers";
        }
        redirectAttributes.addFlashAttribute("addSupplierSuccess", true);
        return "redirect:/home/suppliers";

    }

    @GetMapping("/deletesupplier/{id}")
    public String deleteSupplier(@PathVariable Long id, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        boolean supplierDeleted = supplierServiceImplementation.deleteSupplier(id);
        if (supplierDeleted) {
            redirectAttributes.addFlashAttribute("deleteSupplierSuccess", true);
            return "redirect:/home/suppliers";
        }
        redirectAttributes.addFlashAttribute("deleteeSupplierFailed", true);
        return "redirect:/home/suppliers";
    }

    @GetMapping("/updatesupplier/{id}")
    public String updateSupplier(@PathVariable Long id, HttpSession httpSession, Model model, RedirectAttributes redirectAttributes){
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("cPage", "supplier");
        if(id==null){
            model.addAttribute("supplier",new Suppliers());
            return "update-supplier";
        }
        Suppliers supplier = supplierServiceImplementation.getSupplier(id);
        model.addAttribute("supplier",supplier);
        return "update-supplier";
    }
    @PostMapping("/updatesupplier")
    public String updateSupplier(@ModelAttribute Suppliers suppliers, HttpSession httpSession, Model model,RedirectAttributes redirectAttributes){
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        boolean updateSuccess =supplierServiceImplementation.updateSupplier(suppliers.getSupplierId(),suppliers);
        if(updateSuccess){
            redirectAttributes.addFlashAttribute("updateSupplierSuccess",true);
            return "redirect:/home/suppliers";
        }
        redirectAttributes.addFlashAttribute("updateSupplierFailed",true);
        return "redirect:/home/suppliers";
    }
}

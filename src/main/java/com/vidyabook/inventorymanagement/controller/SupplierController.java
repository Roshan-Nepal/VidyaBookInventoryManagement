package com.vidyabook.inventorymanagement.controller;

import com.vidyabook.inventorymanagement.entity.Suppliers;
import com.vidyabook.inventorymanagement.service.SupplierServiceImplementation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.vidyabook.inventorymanagement.controller.BookController.checkSession;

@Controller
public class SupplierController {
    private final SupplierServiceImplementation supplierServiceImplementation;

    public SupplierController(SupplierServiceImplementation supplierServiceImplementation) {
        this.supplierServiceImplementation = supplierServiceImplementation;
    }

    @GetMapping("/home/suppliers")
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

    @GetMapping("/home/addsupplier")
    public String addSupplier(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!checkSession(httpSession, redirectAttributes)) {
            return "redirect:/login";
        }
        model.addAttribute("cPage", "supplier");
        model.addAttribute("supplier", new Suppliers());
        return "addsupplier";
    }

    @PostMapping("/home/addsupplier")
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

    @GetMapping("/home/deletesupplier/{id}")
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

    @GetMapping("/home/updatesupplier/{id}")
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
    @PostMapping("/home/updatesupplier")
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

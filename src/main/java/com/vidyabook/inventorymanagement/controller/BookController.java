package com.vidyabook.inventorymanagement.controller;

import com.vidyabook.inventorymanagement.dto.book.BookDto;
import com.vidyabook.inventorymanagement.dto.book.BookRequestDto;
import com.vidyabook.inventorymanagement.entity.Book;
import com.vidyabook.inventorymanagement.entity.BookLog;
import com.vidyabook.inventorymanagement.service.BookServiceImplementation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class BookController {
    private final BookServiceImplementation bookServiceImplementation;

    public BookController(BookServiceImplementation bookServiceImplementation) {
        this.bookServiceImplementation = bookServiceImplementation;
    }

    @GetMapping("/home/getBook")
    public String getBook(Model model, HttpSession httpSession,RedirectAttributes redirectAttributes) {
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
        model.addAttribute("bookRequestDto", new BookRequestDto());
        model.addAttribute("cPage", "getBook");
        return "searchbook";

    }

    @PostMapping("/home/getBook")
    public String getBook(@ModelAttribute("bookRequestDto") BookRequestDto bookRequestDto, Model model, RedirectAttributes redirectAttributes,HttpSession httpSession) {
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
        List<BookDto> foundBook = bookServiceImplementation.getBook(bookRequestDto.getIdentifier(), redirectAttributes);
        if (foundBook.isEmpty()) {
            redirectAttributes.addFlashAttribute("bookNotFound", true);
            return "redirect:/home/getBook";
        }
        model.addAttribute("bookFound",true);
        model.addAttribute("book",new Book());
        model.addAttribute("foundBook", foundBook);
        model.addAttribute("cPage", "getBook");
        return "searchBook";
    }

    @GetMapping("/home/allBook")
    public String getAllBook(Model model, RedirectAttributes redirectAttributes, HttpSession httpSession, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
        Pageable paging = PageRequest.of(page - 1, size);
        Page<BookDto> pages = bookServiceImplementation.getAllBook(paging);
        List<BookDto> books = pages.getContent();
        model.addAttribute("books",books);
        model.addAttribute("book",new Book());
        model.addAttribute("currentPage", pages.getNumber() + 1);
        model.addAttribute("totalItems",pages.getTotalElements());
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("pageSize",size);
        model.addAttribute("cPage", "allBook");
        return "allbook";
    }

    @GetMapping("/home/addBook")
    public String addBook(Model model,RedirectAttributes redirectAttributes,HttpSession httpSession){
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
        model.addAttribute("book",new Book());
        model.addAttribute("visitAddBook",true);
        model.addAttribute("cPage", "addBook");
        return "addbook";
    }

    @PostMapping("/home/addBook")
    public String addBook(@ModelAttribute("book") Book book, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession, BindingResult result){
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("addBookFailed",true);
            return "redirect:/home/addBook";
        }
//        Long bookId =  book.getId();
//        book.setId(bookId);
        boolean addSuccess = bookServiceImplementation.addBook(book,redirectAttributes);
        model.addAttribute("cPage", "addBook");
        if(!addSuccess){
            redirectAttributes.addFlashAttribute("addBookFailed",true);
            return "redirect:/home/addBook";
        }
        model.addAttribute("addMessage","BOOK ADDED SUCCESSFULLY!");
        redirectAttributes.addFlashAttribute("addBookSuccess",true);
        return "redirect:/home/addBook";
    }

    @GetMapping("/home/updateBook/{id}")
    public String updateBook(Model model,RedirectAttributes redirectAttributes,HttpSession httpSession,@PathVariable(name = "id") Long id){
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
//        model.addAttribute("currentPage", "allBook");
        if(id==null){
            model.addAttribute("book",new Book());
            return "updatebook";
        }
        Book book = bookServiceImplementation.bookWithId(id);
        model.addAttribute("book", book);
        model.addAttribute("visitAddBook",true);
        return "updatebook";
    }
    @PostMapping("/home/updateBook")
    public String updateBook(@ModelAttribute("book") Book book, Model model,RedirectAttributes redirectAttributes){
//        model.addAttribute("currentPage", "allBook");
        boolean success =bookServiceImplementation.updateBook(book.getId(),book);
        if(success){
            redirectAttributes.addFlashAttribute("updateBookSuccess",true);
            return "redirect:/home/allBook";
        }
        redirectAttributes.addFlashAttribute("updateBookFailed",true);
        return "redirect:/home/allBook";
    }

    @GetMapping("/home/deleteBook/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes){
        boolean success =bookServiceImplementation.deleteBook(id);
        if(success){
            redirectAttributes.addFlashAttribute("deleteBookSuccess",true);
            return "redirect:/home/allBook";
        }
        redirectAttributes.addFlashAttribute("deleteeBookFailed",true);
        return "redirect:/home/allBook";
    }
    @GetMapping("/home/history")
    public String histroy(Model model, RedirectAttributes redirectAttributes,HttpSession httpSession){
        if(!checkSession(httpSession,redirectAttributes)){
            return "redirect:/login";
        }
        List<BookLog> bookLogs = bookServiceImplementation.getLogs();
        model.addAttribute("logList",bookLogs);
        model.addAttribute("cPage", "viewHistory");
        return "history";
    }

    public static boolean checkSession(HttpSession httpSession, RedirectAttributes redirectAttributes){
        if (httpSession.getAttribute("username") == null) {
            httpSession.invalidate();
            redirectAttributes.addFlashAttribute("loginRequired",true);
            return false;
        }
        return true;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}


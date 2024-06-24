package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.dto.book.BookDto;
import com.vidyabook.inventorymanagement.entity.Book;
import com.vidyabook.inventorymanagement.entity.BookLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface BookService {
//    BookResponseDto getProduct(String id, BookRequestDto bookRequestDto, Model model);

    List<BookDto> getBook(String id, RedirectAttributes redirectAttributes);

    Page<BookDto> getAllBook(Pageable pageable);

    boolean addBook(Book book, RedirectAttributes redirectAttributes);

    Book bookWithId(Long id);

    boolean updateBook(Long id,Book book);
    boolean deleteBook(Long id);

    List<BookLog> getLogs();
}

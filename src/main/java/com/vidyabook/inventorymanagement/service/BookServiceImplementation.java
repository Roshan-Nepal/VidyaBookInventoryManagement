package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.dto.book.BookDtoMapper;
import com.vidyabook.inventorymanagement.dto.book.BookDto;
import com.vidyabook.inventorymanagement.entity.Book;
import com.vidyabook.inventorymanagement.entity.BookLog;
import com.vidyabook.inventorymanagement.repository.BookLogRepository;
import com.vidyabook.inventorymanagement.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;
    private final BookLogRepository bookLogRepository;
    private BookLog bookLog;
    HttpSession session;

    //    private  BookResponseDto bookResponseDto;
    public BookServiceImplementation(BookRepository bookRepository,BookDtoMapper bookDtoMapper,BookLogRepository bookLogRepository,HttpSession session) {
        this.bookRepository = bookRepository;
        this.bookDtoMapper = bookDtoMapper;
        this.bookLogRepository = bookLogRepository;
        this.session = session;
    }


    @Override
    public List<BookDto> getBook(String identifier, RedirectAttributes redirectAttributes) {
        List<BookDto> books = bookRepository.findBookByIdentifier(identifier).stream().map(bookDtoMapper).collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("bookFound", true);
        return books;

    }

    @Override
    public Page<BookDto> getAllBook(Pageable pageable) {
//        return bookRepository.findAll(pageable).stream().map(bookDtoMapper).collect(Collectors.toList());
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<BookDto> bookDtos = bookPage.stream().map(bookDtoMapper).collect(Collectors.toList());
        return new PageImpl<>(bookDtos, pageable, bookPage.getTotalElements());
    }

    @Override
    public boolean addBook(Book book, RedirectAttributes redirectAttributes) {
        try {
            if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
                redirectAttributes.addFlashAttribute("idDuplicate", true);
                return false;
            }
            Book addedBook = bookRepository.save(book);
            bookLog = new BookLog();
            bookLog.setBook(book.getTitle());
            bookLog.setUser((String)session.getAttribute("username"));
            bookLog.setAction("ADDED");
            bookLogRepository.save(bookLog);
            return bookRepository.findById(addedBook.getId()).isPresent();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("idDuplicate", true);
            return false;
        }


    }

    @Override
    public Book bookWithId(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id).get();
        }
        return new Book();
    }

    @Override
    public boolean updateBook(Long id,Book book) {
//        int updatedRows = bookRepository.updateBookById(id, book);
        Book bookTOUpdate = bookRepository.getReferenceById(id);
        bookTOUpdate.setAuthor(book.getAuthor());
        bookTOUpdate.setTitle(book.getTitle());
        bookTOUpdate.setPrice(book.getPrice());
        bookTOUpdate.setQuantity(book.getQuantity());
        bookTOUpdate.setPublishedDate(book.getPublishedDate());
        bookTOUpdate.setDescription(book.getDescription());
        bookLog = new BookLog();
        bookLog.setBook(book.getTitle());
        bookLog.setUser((String)session.getAttribute("username"));
        bookLog.setAction("UPDATED");
        bookLogRepository.save(bookLog);
        Book up = bookRepository.save(bookTOUpdate);
//        return  updatedRows > 0;
        return Objects.equals(up.getId(), book.getId());
    }

    @Override
    public boolean deleteBook(Long id) {
        bookLog = new BookLog();
        bookLog.setBook(bookRepository.getReferenceById(id).getTitle());
        bookLog.setUser((String)session.getAttribute("username"));
        bookLog.setAction("DELETED");
        bookLogRepository.save(bookLog);
        bookRepository.deleteById(id);
        Optional<Book> check =bookRepository.findById(id);
        return check.isEmpty();
    }

    @Override
    public List<BookLog> getLogs() {
//        return bookLogRepository.findAll(Sort.by(Sort.Direction.ASC,"performed_time"));
        return bookLogRepository.findAll(Sort.by(Sort.Direction.DESC, "performedTime"));
    }


//    @Override
//    public Optional<Book> getBooks(){
//        return
//    }
}

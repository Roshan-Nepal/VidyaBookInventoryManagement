package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.dto.homestat.HomeStatDto;
import com.vidyabook.inventorymanagement.dto.book.BookDtoMapper;
import com.vidyabook.inventorymanagement.dto.book.BookDto;
import com.vidyabook.inventorymanagement.entity.Book;
import com.vidyabook.inventorymanagement.entity.BookLog;
import com.vidyabook.inventorymanagement.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;
    private final BookLogRepository bookLogRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;
    private BookLog bookLog;
    HttpSession session;

    //    private  BookResponseDto bookResponseDto;
    public BookServiceImplementation(BookRepository bookRepository, BookDtoMapper bookDtoMapper, BookLogRepository bookLogRepository, HttpSession session,
                                     SupplierRepository supplierRepository, UserRepository userRepository,SaleRepository saleRepository) {
        this.bookRepository = bookRepository;
        this.bookDtoMapper = bookDtoMapper;
        this.bookLogRepository = bookLogRepository;
        this.session = session;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
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
            bookLog.setUser((String) session.getAttribute("username"));
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
    public boolean updateBook(Long id, Book book) {

        Book bookToUpdate = bookRepository.getReferenceById(id);
        if (bookToUpdate.getTitle().equals(book.getTitle())
                && bookToUpdate.getAuthor().equals(book.getAuthor())
                && Objects.equals(bookToUpdate.getPrice(), book.getPrice())
                && Objects.equals(bookToUpdate.getQuantity(), book.getQuantity())
                && Objects.equals(bookToUpdate.getMinStockLevel(), book.getMinStockLevel())
                && Objects.equals(bookToUpdate.getPublishedDate(), book.getPublishedDate())
                && Objects.equals(bookToUpdate.getDescription(), book.getDescription())) {
                return false;
        }
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setQuantity(book.getQuantity());
        bookToUpdate.setMinStockLevel(book.getMinStockLevel());
        bookToUpdate.setPublishedDate(book.getPublishedDate());
        bookToUpdate.setDescription(book.getDescription());
        bookLog = new BookLog();
        bookLog.setBook(book.getTitle());
        bookLog.setUser((String) session.getAttribute("username"));
        bookLog.setAction("UPDATED");
        bookLogRepository.save(bookLog);
        Book up = bookRepository.save(bookToUpdate);
        return Objects.equals(up.getId(), book.getId());
    }

    @Override
    public boolean deleteBook(Long id) {
        bookLog = new BookLog();
//        bookLog.setBook(bookRepository.getReferenceById(id).getTitle());
//        bookLog.setUser((String) session.getAttribute("username"));
//        bookLog.setAction("DELETED");
//        bookLogRepository.save(bookLog);
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null || saleRepository.findByBook(book.getTitle())){
            return false;
        }
        bookRepository.deleteById(id);
        Optional<Book> check = bookRepository.findById(id);
        return check.isEmpty();
    }

    @Override
    public List<BookLog> getLogs() {
        return bookLogRepository.findAll(Sort.by(Sort.Direction.DESC, "performedTime"));
    }

    public HomeStatDto home() {
        long totalBooks = bookRepository.count();
        long totalSuppliers = supplierRepository.count();
        long totalUsers = userRepository.count();

        return new HomeStatDto(totalBooks, totalSuppliers, totalUsers);
    }

    @Override
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }
    @Override
    public List<String> getAllBookTitles() {
        return bookRepository.findAll()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

}

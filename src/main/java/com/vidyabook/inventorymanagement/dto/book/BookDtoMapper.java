package com.vidyabook.inventorymanagement.dto.book;

import com.vidyabook.inventorymanagement.entity.Book;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class BookDtoMapper implements Function<Book, BookDto> {
    @Override
    public BookDto apply(Book book){
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedDate(),
                book.getPrice(),
                book.getQuantity(),
                book.getDescription()
        );
    }
}

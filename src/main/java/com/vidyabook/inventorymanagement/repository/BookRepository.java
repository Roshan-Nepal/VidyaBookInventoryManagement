package com.vidyabook.inventorymanagement.repository;

import com.vidyabook.inventorymanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    Page<Book> findAll(Pageable pageable);

    @Query("select b from book b where b.title = :title")
    Optional<Book> findByTitle(String title);

    @Query("select b from book b where b.title = :identifier or b.author = :identifier")
    List<Book> findBookByIdentifier(String identifier);
    @Transactional
    @Modifying
    @Query("UPDATE book b SET b.title = :#{#book.title}, b.author = :#{#book.author}, b.description = :#{#book.description}, b.price = :#{#book.price}, b.quantity = :#{#book.quantity}, b.publishedDate = :#{#book.publishedDate} WHERE b.id = :id")
    int updateBookById(Long id,Book book);
}

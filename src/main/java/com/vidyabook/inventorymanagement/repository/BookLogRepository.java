package com.vidyabook.inventorymanagement.repository;

import com.vidyabook.inventorymanagement.entity.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLogRepository extends JpaRepository<BookLog,Long> {
}

package com.larysoft.library.repository;

import com.larysoft.library.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BookRepository extends JpaRepository<Books, Long> {

    @Modifying//when using dml statements @modifying annotation must me used to indicate the type of query to JPA
    @Query("delete from books b where b.bookId = :bookId")
    @Transactional/* using @transactional annotation to ensure data integrity after data manipulation */
    int deleteBook(@Param("bookId") Long bookId);
}

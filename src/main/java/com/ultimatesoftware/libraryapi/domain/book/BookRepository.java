package com.ultimatesoftware.libraryapi.domain.book;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Set<Book> findBooksByAuthor(String author);

}

package ua.challenge.service.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ua.challenge.entity.elasticsearch.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    void delete(Book book);

    Iterable<Book> findAll();

    Page<Book> findByAuthor(String author, PageRequest pageRequest);

    List<Book> findByTitle(String title);

    Page<Book> findByAuthorWithCustomQuery(String author, PageRequest pageRequest);

    List<Book> findByTitleWithRegexp(String title);
}

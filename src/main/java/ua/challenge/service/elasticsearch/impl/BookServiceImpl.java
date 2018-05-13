package ua.challenge.service.elasticsearch.impl;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import ua.challenge.entity.elasticsearch.Book;
import ua.challenge.repository.elasticsearch.BookRepository;
import ua.challenge.service.elasticsearch.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public Book findOne(String id) {
        return bookRepository.findOne(id);
    }

    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Page<Book> findByAuthor(String author, PageRequest pageRequest) {
        return bookRepository.findByAuthor(author, pageRequest);
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Page<Book> findByAuthorWithCustomQuery(String author, PageRequest pageRequest) {
        return bookRepository.findByAuthorWithCustomQuery(author, pageRequest);
    }

    @Override
    public List<Book> findByTitleWithRegexp(String title) {
        String regexp = ".*(" + title.toLowerCase() + ").*";

        SearchQuery query = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.regexpQuery("title", regexp))
                .build();

        return elasticsearchTemplate.queryForList(query, Book.class);
    }
}

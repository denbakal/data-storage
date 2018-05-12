package ua.challenge.service.elasticsearch.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ua.challenge.entity.elasticsearch.Book;
import ua.challenge.entity.elasticsearch.FieldIndex;
import ua.challenge.entity.elasticsearch.Publisher;
import ua.challenge.service.elasticsearch.BookService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceImplTest {
    @Autowired
    private BookService bookService;



    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Before
    public void before() {
        esTemplate.deleteIndex(FieldIndex.class);
        esTemplate.createIndex(FieldIndex.class);
        esTemplate.putMapping(FieldIndex.class);
        esTemplate.refresh(FieldIndex.class);

//        esTemplate.deleteIndex(Book.class);
//        esTemplate.createIndex(Book.class);
//        esTemplate.putMapping(Book.class);
//        esTemplate.refresh(Book.class);
    }

    @Test
    public void testSave() {
        System.out.println("Test");
//        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), new Publisher("CoolPrint"));
//        Book testBook = bookService.save(book);

//        assertThat(testBook.getId()).isNotNull();
//        assertThat(testBook.getTitle()).isEqualTo(book.getTitle());
//        assertThat(testBook.getAuthor()).isEqualTo(book.getAuthor());
//        assertThat(testBook.getReleaseDate()).isEqualTo(book.getReleaseDate());
    }

    @Test
    public void testFindOne() {
        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), new Publisher("CoolPrint"));
        bookService.save(book);

        Book testBook = bookService.findOne(book.getId());

        assertThat(testBook.getId()).isNotNull();
        assertThat(testBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(testBook.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(testBook.getReleaseDate()).isEqualTo(book.getReleaseDate());
    }

    @Test
    public void testFindByTitle() {
        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), new Publisher("CoolPrint"));
        bookService.save(book);

        List<Book> byTitle = bookService.findByTitle(book.getTitle());
        assertThat(byTitle.size()).isEqualTo(1);
    }

    @Test
    public void testFindByAuthor() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("1001", "Elasticsearch Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));
        bookList.add(new Book("1002", "Apache Lucene Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));
        bookList.add(new Book("1003", "Apache Solr Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));
        bookList.add(new Book("1007", "Spring Data + ElasticSearch", "Ken Burkley", new Date(), new Publisher("CoolPrint")));
        bookList.add(new Book("1008", "Spring Boot + MongoDB", "Tom Lee", new Date(), new Publisher("CoolPrint")));

        for (Book book : bookList) {
            bookService.save(book);
        }

        Page<Book> result = bookService.findByAuthor("Ken Burkley", new PageRequest(0, 10));
        assertThat(result.getTotalElements()).isEqualTo(4);

        Page<Book> result2 = bookService.findByAuthor("Tom Lee", new PageRequest(0, 10));
        assertThat(result2.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void testFindByAuthorWithCustomQuery() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("1001", "Elasticsearch Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));
        bookList.add(new Book("1002", "Apache Lucene Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));

        for (Book book : bookList) {
            bookService.save(book);
        }

        Page<Book> result = bookService.findByAuthorWithCustomQuery("Ken Burkley", new PageRequest(0, 10));
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void testFindByTitleWithRegexp() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(new Book("1001", "Elasticsearch Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));
        bookList.add(new Book("1002", "Apache Lucene Basics", "Ken Burkley", new Date(), new Publisher("CoolPrint")));

        for (Book book : bookList) {
            bookService.save(book);
        }

        List<Book> result = bookService.findByTitleWithRegexp("Lucene");
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testDelete() {
        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", new Date(), new Publisher("CoolPrint"));
        bookService.save(book);
        bookService.delete(book);
        Book testBook = bookService.findOne(book.getId());
        assertThat(testBook).isNull();
    }
}

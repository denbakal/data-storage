package ua.challenge.repository.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.challenge.entity.elasticsearch.Book;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

    Page<Book> findByAuthor(String author, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"author\": \"?0\"}}]}}")
    Page<Book> findByAuthorWithCustomQuery(String author, Pageable pageable);

    List<Book> findByTitle(String title);
}

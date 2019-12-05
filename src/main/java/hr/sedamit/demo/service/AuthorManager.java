package hr.sedamit.demo.service;

import hr.sedamit.demo.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorManager {

    Page<Author> getAllAuthors(Pageable pageable);

    Optional<Author> getAuthor(Long authorID);

    Author save(Author author);

    void delete(Long authorId);
}

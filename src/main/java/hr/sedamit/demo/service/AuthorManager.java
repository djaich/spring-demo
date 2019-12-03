package hr.sedamit.demo.service;

import hr.sedamit.demo.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorManager {

    List<Author> getAllAuthors();

    Optional<Author> getAuthor(Long authorID);

    Author save(Author author);

    void delete(Long authorId);
}

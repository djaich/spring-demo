package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.AuthorRepository;
import hr.sedamit.demo.model.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("!test")
public class DefaultAuthorManager implements AuthorManager {

    private AuthorRepository repository;

    public DefaultAuthorManager(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Author> getAllAuthors() {
        log.info("Listing authors: " + repository.findAll());
        return repository.findAll();
    }

    @Override
    public Optional<Author> getAuthor(Long authorId) {
        return repository.findById(authorId);
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return repository.save(author);
    }

    @Override
    public void delete(Long authorId) {
        repository.deleteById(authorId);

    }

    @PostConstruct
    public void init() {
        log.info("Default author manager ready");
    }
}

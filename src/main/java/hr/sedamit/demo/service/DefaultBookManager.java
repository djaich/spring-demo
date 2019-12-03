package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.BookRepository;
import hr.sedamit.demo.model.Book;
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
public class DefaultBookManager implements BookManager {

    private BookRepository repository;

    public DefaultBookManager(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Listing books: " + repository.findAll());
        return repository.findAll();
    }

    @Override
    public Optional<Book> getBook(Long bookId) {
        return repository.findById(bookId);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public void delete(Long bookId) {
        repository.deleteById(bookId);

    }

    @PostConstruct
    public void init() {
        log.info("Default book manager ready");
    }
}

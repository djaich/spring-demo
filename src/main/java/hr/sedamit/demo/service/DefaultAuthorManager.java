package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.AuthorRepository;
import hr.sedamit.demo.dao.AuthorSpecifications;
import hr.sedamit.demo.model.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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
    @Cacheable(CacheNames.CACHE_AUTHOR_LIST)
    public Page<Author> getAllAuthors(Pageable pageable) {
        return repository.findAll(AuthorSpecifications.byYearOfBirth(1900), pageable);
    }

    @Override
    @Cacheable(CacheNames.CACHE_AUTHOR_DETAILS)
    public Optional<Author> getAuthor(Long authorId) {
        log.info("Fetching author details from database");
        return repository.findById(authorId);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheNames.CACHE_AUTHOR_DETAILS, key = "#author.id"),
            @CacheEvict(value = CacheNames.CACHE_AUTHOR_LIST, allEntries = true)
    })

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

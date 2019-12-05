package hr.sedamit.demo.web;

import hr.sedamit.demo.model.Author;
import hr.sedamit.demo.service.AuthorManager;
import hr.sedamit.demo.web.commands.UpdateAuthorCommand;
import hr.sedamit.demo.web.dto.AuthorDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@Slf4j
public class AuthorController {

    private AuthorManager authorManager;

    @Value("${author.list.allowed:true}")
    private boolean allowedListAuthors;

    public AuthorController(AuthorManager authorManager) {
        this.authorManager = authorManager;
    }

    @GetMapping(value = "/list")
    public Page<AuthorDTO> listAllAuthors(@PageableDefault(size = 20) Pageable pageable) {

        if (allowedListAuthors) {
            final Page<Author> allAuthors = authorManager.getAllAuthors(pageable);
            return allAuthors.map(DTOFactory::toAuthorDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Author list not allowed!");
        }
    }

    @GetMapping("/{authorId}")
    public AuthorDTO showAuthorDetails(@PathVariable Long authorId) {
        Optional<Author> optionalAuthor = authorManager.getAuthor(authorId);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            return DTOFactory.toAuthorDTO(author);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with such id does not exist");
        }
    }

    @PostMapping("/add")
    public AuthorDTO addAuthor(@RequestBody UpdateAuthorCommand authorData) {
        Author author = new Author();
        updateAuthorData(author, authorData);
        return DTOFactory.toAuthorDTO(authorManager.save(author));
    }

    @PutMapping("{authorId}/update")
    public AuthorDTO updateAuthor(@PathVariable Long authorId, @RequestBody UpdateAuthorCommand authorData) {
        Optional<Author> optionalAuthor = authorManager.getAuthor(authorId);
        if (!optionalAuthor.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with such id.");
        }
        Author author = optionalAuthor.get();
        updateAuthorData(author, authorData);
        return DTOFactory.toAuthorDTO(authorManager.save(author));
    }

    @DeleteMapping("/delete/{authorId}")
    public Boolean deleteAuthor(@PathVariable Long authorId) {
        try {
            authorManager.delete(authorId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @PostConstruct
    public void init() {
        log.info("Author controller ready");
    }

    private void updateAuthorData(Author author, UpdateAuthorCommand command) {
        author.setFirstName(command.getFirstName());
        author.setLastName(command.getLastName());
        author.setYearOfBirth(command.getYearOfBirth());
    }

}

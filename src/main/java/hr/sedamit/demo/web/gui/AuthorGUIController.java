package hr.sedamit.demo.web.gui;

import hr.sedamit.demo.model.Author;
import hr.sedamit.demo.service.AuthorManager;
import hr.sedamit.demo.web.commands.UpdateAuthorCommand;
import hr.sedamit.demo.web.dto.AuthorDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/gui/author")
@SessionAttributes("authorData")
public class AuthorGUIController {

    private final AuthorManager authorManager;

    public AuthorGUIController(AuthorManager authorManager) {
        this.authorManager = authorManager;
    }

    @GetMapping("/list")
    public String listAuthors(Model model, Pageable pageable) {

        Page<Author> allAuthors = authorManager.getAllAuthors(pageable);
        Page<AuthorDTO> dtos = allAuthors.map(DTOFactory::toAuthorDTO);
        model.addAttribute("authors", dtos);

        return "author-list";
    }

    @GetMapping("/{authorId}/edit")
    public String editAuthor(Model model, @PathVariable Long authorId) {
        UpdateAuthorCommand authorData;
        if (authorId == 0) {
            authorData = new UpdateAuthorCommand();
        } else {
            Optional<Author> optionalAuthor = authorManager.getAuthor(authorId);
            if (!optionalAuthor.isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with such ID: " + authorId);
            authorData = new UpdateAuthorCommand(optionalAuthor.get());
        }

        model.addAttribute("authorData", authorData);
        return "author-edit";
    }

    @PostMapping("/{authorId}/edit")
    public String processEditAuthor(@ModelAttribute UpdateAuthorCommand authorData,
                                    @PathVariable Long authorId) {

        Author author;
        if (authorId == 0) {
            author = new Author();
        } else {
            Optional<Author> optionalAuthor = authorManager.getAuthor(authorId);
            if (!optionalAuthor.isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with such ID: " + authorId);
            author = optionalAuthor.get();
        }
        updateAuthorData(author, authorData);
        authorManager.save(author);
        return "redirect:/gui/author/list";
    }

    private void updateAuthorData(Author author, UpdateAuthorCommand command) {
        author.setFirstName(command.getFirstName());
        author.setLastName(command.getLastName());
        author.setYearOfBirth(command.getYearOfBirth());
    }

}

package hr.sedamit.demo.web;

import hr.sedamit.demo.model.Author;
import hr.sedamit.demo.model.Book;
import hr.sedamit.demo.service.AuthorManager;
import hr.sedamit.demo.service.BookManager;
import hr.sedamit.demo.web.commands.UpdateBookCommand;
import hr.sedamit.demo.web.dto.BookDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    private BookManager bookManager;
    private AuthorManager authorManager;

    @Value("${book.list.allowed:true}")
    private boolean allowedListBooks;

    public BookController(BookManager bookManager, AuthorManager authorManager) {
        this.bookManager = bookManager;
        this.authorManager = authorManager;
    }

    @GetMapping(value = "/list")
    public List<BookDTO> listAllBooks() {

        if (allowedListBooks) {
            final List<Book> allBooks = bookManager.getAllBooks();
            return allBooks.stream()
                    .map(DTOFactory::toBookDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Book list not allowed!");
        }
    }

    @GetMapping("/{bookId}")
    public BookDTO showBookDetails(@PathVariable Long bookId) {
        Optional<Book> optionalBook = bookManager.getBook(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return DTOFactory.toBookDTO(book);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with such id does not exist");
        }
    }

    @PostMapping("/add")
    public BookDTO addBook(@RequestBody UpdateBookCommand bookData) {
        try {
            Book book = new Book();
            updateBookData(book, bookData);
            return DTOFactory.toBookDTO(bookManager.save(book));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found with id: " + bookData.getAuthorId());
        }
    }

    @PutMapping("{bookId}/update")
    public BookDTO updateBook(@PathVariable Long bookId, @RequestBody UpdateBookCommand bookData) {
        Optional<Book> optionalBook = bookManager.getBook(bookId);
        if (!optionalBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with such id.");
        }
        Book book = optionalBook.get();
        updateBookData(book, bookData);
        return DTOFactory.toBookDTO(bookManager.save(book));
    }

    @DeleteMapping("/delete/{bookId}")
    public Boolean deleteBook(@PathVariable Long bookId) {
        try {
            bookManager.delete(bookId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @PostConstruct
    public void init() {
        log.info("Book controller ready");
    }

    private void updateBookData(Book book, UpdateBookCommand command) {
        book.setTitle(command.getTitle());
        Optional<Author> authorOptional = authorManager.getAuthor(command.getAuthorId());
        if (authorOptional.isPresent()) {
            book.setAuthor(authorOptional.get());
        } else {
            throw new IllegalArgumentException("Wrong author id!");
        }

    }

}

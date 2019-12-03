package hr.sedamit.demo.model;

import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @OneToMany(mappedBy = "author")
    public List<Book> books = new ArrayList<>();
    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    private Long id;
    @Column(length = 200)
    private String firstName;
    private String lastName;
    private int yearOfBirth;
}

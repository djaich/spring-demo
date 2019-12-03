package hr.sedamit.demo.model;

import lombok.AccessLevel;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(length = 2000)
    private String title;

    @ManyToOne
    private Author author;
}

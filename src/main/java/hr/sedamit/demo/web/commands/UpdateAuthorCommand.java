package hr.sedamit.demo.web.commands;

import hr.sedamit.demo.model.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorCommand {

    private String firstName;

    private String lastName;

    private String nationality;

    private int yearOfBirth;

    public UpdateAuthorCommand(Author author) {
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.nationality = author.getNationality();
        this.yearOfBirth = author.getYearOfBirth();
    }
}
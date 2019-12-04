package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private int yearOfBirth;
}

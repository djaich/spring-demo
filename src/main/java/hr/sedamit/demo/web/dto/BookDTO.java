package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private AuthorDTO author;
}

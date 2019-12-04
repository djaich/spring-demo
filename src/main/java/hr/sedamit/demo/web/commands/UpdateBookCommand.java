package hr.sedamit.demo.web.commands;

import lombok.Getter;

@Getter
public class UpdateBookCommand {

    private String title;

    private Long authorId;
}

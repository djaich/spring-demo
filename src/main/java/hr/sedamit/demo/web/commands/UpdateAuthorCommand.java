package hr.sedamit.demo.web.commands;

import lombok.Getter;


@Getter
public class UpdateAuthorCommand {
    private String firstName;
    private String lastName;
    private int yearOfBirth;
}
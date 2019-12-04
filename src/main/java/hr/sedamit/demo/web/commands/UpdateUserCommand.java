package hr.sedamit.demo.web.commands;

import lombok.Getter;

@Getter
public class UpdateUserCommand {

    private String userName;

    private String password;

    private String fullName;

    private transient int age;

    private boolean active;

}

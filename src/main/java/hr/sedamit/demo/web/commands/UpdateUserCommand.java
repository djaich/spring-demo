package hr.sedamit.demo.web.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserCommand {

    private String userName;

    private String password;

    private String fullName;

    private transient int age;

    private boolean active;

    private Long roleId;

}

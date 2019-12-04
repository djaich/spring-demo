package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String userName;

    private String fullName;

    private transient int age;

    private boolean active;
}

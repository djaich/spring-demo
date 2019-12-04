package hr.sedamit.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance
@DiscriminatorColumn(name = "USER_TYPE")
@DiscriminatorValue("USER")
public class User {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    private int age;
    private boolean active;
}

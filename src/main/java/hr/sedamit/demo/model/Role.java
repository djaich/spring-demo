package hr.sedamit.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "ROLE_ID"))
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions;
}

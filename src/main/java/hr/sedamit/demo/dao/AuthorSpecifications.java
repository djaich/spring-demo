package hr.sedamit.demo.dao;

import hr.sedamit.demo.model.Author;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecifications {

    public static Specification<Author> byYearOfBirth(Integer years) {
        return (root, criteriaQuery, cb) -> cb.equal(root.get("yearOfBirth"), years);
    }

}

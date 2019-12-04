package hr.sedamit.demo.dao;

import hr.sedamit.demo.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class UserSpecifications {

    public static Specification<User> activeUsers() {
        return (Specification<User>) (root, criteriaQuery, cb) -> {
            return cb.equal(root.get("active"), Boolean.TRUE);
        };
    }

    public static Specification<User> olderThanAge(Integer years) {
        return (Specification<User>) (root, criteriaQuery, cb) -> {
            return cb.greaterThanOrEqualTo(root.get("age"), years);
        };
    }


    public static Specification<User> findUsers(UserSearchFilter filter) {
/*
        Specification<User> active = activeUsers();
        if (filter.getOlderThanAge() != null)
            active = active.and(olderThanAge(filter.getOlderThanAge()));
        return active;*/

        Specification<User> spec = (Specification<User>) (root, criteriaQuery, cb) -> cb.and(new Predicate[0]);

        if (filter.getActive() != null)
            spec = spec.and(activeUsers());

        if (filter.getOlderThanAge() != null)
            spec = spec.and(olderThanAge(filter.getOlderThanAge()));

        return spec;
      /*  return (Specification<User>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getActive() != null)
                predicates.add(cb.equal(root.get("active"), filter.getActive()));

            if(filter.getOlderThanAge() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("age"), filter.getOlderThanAge()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };*/
    }

}

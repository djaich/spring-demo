package hr.sedamit.demo.dao;

import lombok.Getter;

@Getter
public class UserSearchFilter {

    private Boolean active;

    private Integer olderThanAge;

    public UserSearchFilter(Boolean active, Integer olderThanAge) {
        this.active = active;
        this.olderThanAge = olderThanAge;
    }
}

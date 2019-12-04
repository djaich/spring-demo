package hr.sedamit.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member extends User {

    private String memberId;

    @Embedded
    private Address address;

/*
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "city", column = "")
    )
    private Address billingAddress;



    @CollectionTable(name = "member_adresses")
    private List<Address> addresses;
*/

}

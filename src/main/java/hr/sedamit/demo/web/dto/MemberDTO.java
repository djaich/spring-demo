package hr.sedamit.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO extends UserDTO {

    private String memberId;

    private AddressDTO address;

    public MemberDTO(Long id, String userName, String fullName, int age, boolean active, String memberId, AddressDTO address) {
        super(id, userName, fullName, age, active);
        this.memberId = memberId;
        this.address = address;
    }
}

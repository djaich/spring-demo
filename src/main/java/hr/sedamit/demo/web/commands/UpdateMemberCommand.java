package hr.sedamit.demo.web.commands;

import hr.sedamit.demo.web.dto.AddressDTO;
import lombok.Getter;

@Getter
public class UpdateMemberCommand extends UpdateUserCommand {

    private String memberId;

    private AddressDTO address;

    public UpdateMemberCommand(String memberId, AddressDTO address) {
        this.memberId = memberId;
        this.address = address;
    }
}

package hr.sedamit.demo.web;

import hr.sedamit.demo.model.Address;
import hr.sedamit.demo.model.Member;
import hr.sedamit.demo.service.MemberManager;
import hr.sedamit.demo.web.commands.UpdateMemberCommand;
import hr.sedamit.demo.web.dto.AddressDTO;
import hr.sedamit.demo.web.dto.DTOFactory;
import hr.sedamit.demo.web.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private MemberManager memberManager;

    @Value("${member.list.allowed:true}")
    private boolean allowedListMembers;

    public MemberController(MemberManager memberManager) {
        this.memberManager = memberManager;
    }

    @GetMapping(value = "/list")
    public List<MemberDTO> listAllMembers() {

        if (allowedListMembers) {
            final List<Member> allMembers = memberManager.getAllMembers();
            return allMembers.stream()
                    .map(DTOFactory::toMemberDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Member list not allowed!");
        }
    }

    @GetMapping("/{memberId}")
    public MemberDTO showMemberDetails(@PathVariable Long memberId) {
        Optional<Member> optionalMember = memberManager.getMember(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return DTOFactory.toMemberDTO(member);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member with such id does not exist");
        }
    }

    @PostMapping("/add")
    public MemberDTO addMember(@RequestBody UpdateMemberCommand memberData) {
        Member member = new Member();
        updateMemberData(member, memberData, true);
        return DTOFactory.toMemberDTO(memberManager.save(member));
    }

    @PutMapping("{memberId}/update")
    public MemberDTO updateMember(@PathVariable Long memberId, @RequestBody UpdateMemberCommand memberData) {
        Optional<Member> optionalMember = memberManager.getMember(memberId);
        if (!optionalMember.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No member with such id.");
        }
        Member member = optionalMember.get();
        updateMemberData(member, memberData, false);
        return DTOFactory.toMemberDTO(memberManager.save(member));
    }

    @DeleteMapping("/delete/{memberId}")
    public Boolean deleteMember(@PathVariable Long memberId) {
        try {
            memberManager.delete(memberId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @PostConstruct
    public void init() {
        log.info("Member controller ready");
    }

    private void updateMemberData(Member member, UpdateMemberCommand command, boolean isNew) {
        member.setUserName(command.getUserName());
        member.setFullName(command.getFullName());
        if (isNew)
            member.setPassword(command.getPassword());
        member.setActive(command.isActive());
        member.setAge(command.getAge());
        member.setMemberId(command.getMemberId());
        member.setAddress(toAddress(command.getAddress()));
    }

    private Address toAddress(AddressDTO dto) {
        return new Address(dto.getCountry(), dto.getCity(), dto.getStreet(), dto.getStreetNumber());
    }

}

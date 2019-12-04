package hr.sedamit.demo.service;

import hr.sedamit.demo.dao.MemberRepository;
import hr.sedamit.demo.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("!test")
public class DefaultMemberManager implements MemberManager {

    private MemberRepository repository;

    public DefaultMemberManager(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Member> getAllMembers() {
        log.info("Listing members: " + repository.findAll());
        return repository.findAll();
    }

    @Override
    public Optional<Member> getMember(Long memberId) {
        return repository.findById(memberId);
    }

    @Override
    @Transactional
    public Member save(Member member) {
        return repository.save(member);
    }

    @Override
    public void delete(Long memberId) {
        repository.deleteById(memberId);

    }

    @PostConstruct
    public void init() {
        log.info("Default member manager ready");
    }
}

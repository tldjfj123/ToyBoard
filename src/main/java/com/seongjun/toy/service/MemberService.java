package com.seongjun.toy.service;

import com.seongjun.toy.domain.Member;
import com.seongjun.toy.dto.request.MemberRequest;
import com.seongjun.toy.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member registerMember(MemberRequest request) {
        Member member = new Member();

        member.setUserId(request.getUserId());
        member.setPassword(request.getPassword());
        member.setMail(request.getMail());
        member.setAddress(request.getAddress());

        return memberRepository.save(member);
    }

    public Optional<Member> getMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Long memberId, MemberRequest request) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isPresent()) {
            Member member = foundMember.get();
            member.setUserId(request.getUserId());
            member.setPassword(request.getPassword());
            member.setAddress(request.getAddress());
            member.setMail(request.getMail());

            return memberRepository.save(member);
        }
        throw new IllegalArgumentException("업데이트할 멤버를 찾을 수 없어욧!!");
    }

    public boolean deleteMember(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);

        if (foundMember.isPresent()) {
            memberRepository.deleteById(memberId);
            return true;
        }

        return false;
    }
}

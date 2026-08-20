package com.srijon.library.service.impl;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.entity.Member;
import com.srijon.library.exception.MemberNotFoundException;
import com.srijon.library.mapper.MemberMapper;
import com.srijon.library.repository.MemberRepository;
import com.srijon.library.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    //Calling the objects
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    //Constructor injection
    public MemberServiceImpl(MemberRepository memberRepository , MemberMapper memberMapper){
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberResponseDto addMember(MemberRequestDto memberRequestDto) {

        Member member = memberMapper.toEntity(memberRequestDto);

        Member saveMember = memberRepository.save(member);
        return memberMapper.toResponseDto(saveMember);
    }

    @Override
    public List<MemberResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();

        return members
                .stream()
                .map(memberMapper :: toResponseDto)
                .toList();
    }

    @Override
    public MemberResponseDto getMemberById(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new MemberNotFoundException("Member not found with id : " + id)
                );
        return memberMapper.toResponseDto(member);
    }

    @Override
    public MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new MemberNotFoundException("Member not found with id : " + id)
                );

        //Updating the member
        member.setName(memberRequestDto.getName());
        member.setPhone(memberRequestDto.getPhone());
        member.setEmail(memberRequestDto.getEmail());
        member.setMembershipDate(memberRequestDto.getMembershipDate());

        //Saving the member
        Member updateMember = memberRepository.save(member);

        //Giving back response object
        return memberMapper.toResponseDto(updateMember);
    }

    @Override
    public void deleteMember(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(
                        () -> new MemberNotFoundException("Member not found with id : " + id)
                );

        memberRepository.delete(member);
    }

}

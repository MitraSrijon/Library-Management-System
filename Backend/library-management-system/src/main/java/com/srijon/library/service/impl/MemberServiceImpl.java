package com.srijon.library.service.impl;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import com.srijon.library.entity.Member;
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
        
    }

    @Override
    public MemberResponseDto getMemberById(Long id) {
        return null;
    }

    @Override
    public MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto) {
        return null;
    }

    @Override
    public void deleteMember(Long id) {

    }
}

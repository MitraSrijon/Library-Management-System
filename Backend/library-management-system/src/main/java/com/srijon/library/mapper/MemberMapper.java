package com.srijon.library.mapper;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import com.srijon.library.entity.Book;
import com.srijon.library.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    //Request
    public Member toEntity(MemberRequestDto memberRequestDto){

        Member member = new Member();

        member.setName(memberRequestDto.getName());
        member.setEmail(memberRequestDto.getEmail());
        member.setPhone(memberRequestDto.getPhone());
        member.setMembershipDate(memberRequestDto.getMembershipDate());

        return member;
    }

    //Response
    public MemberResponseDto toResponseDto(Member member){

        MemberResponseDto memberResponseDto = new MemberResponseDto();

        memberResponseDto.setId(member.getId());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setPhone(member.getPhone());
        memberResponseDto.setMembershipDate(member.getMembershipDate());

        return memberResponseDto;
    }
}

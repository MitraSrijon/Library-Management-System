package com.srijon.library.service;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;

public interface MemberService {

    //Create a member
    MemberResponseDto addMember(MemberRequestDto memberRequestDto);

    //Get all the members
    List<MemberResponseDto> getAllMembers();

    //Get members by id
    MemberResponseDto getMemberById(Long id);

    //Update a member
    MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto);

    //Delete a member
    void deleteMember(Long id);
}

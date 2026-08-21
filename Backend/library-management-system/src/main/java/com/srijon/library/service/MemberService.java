package com.srijon.library.service;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

public interface MemberService {

    //Create a member
    MemberResponseDto addMember(MemberRequestDto memberRequestDto);

    //Get all the members
    Page<MemberResponseDto> getAllMembers(Pageable pageable);

    //Get members by id
    MemberResponseDto getMemberById(Long id);

    //Update a member
    MemberResponseDto updateMember(Long id, MemberRequestDto memberRequestDto);

    //Delete a member
    void deleteMember(Long id);

    //Search a member
    Page<MemberResponseDto> searchMember(
            String query, Pageable pageable
    );
}

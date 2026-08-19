package com.srijon.library.controller;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import com.srijon.library.entity.Member;
import com.srijon.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //Logic of adding members
    @PostMapping
    public ResponseEntity<MemberResponseDto> addMember(
                @Valid @RequestBody MemberRequestDto memberRequestDto
            ){

        MemberResponseDto saveMember = memberService.addMember(memberRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saveMember);
    }

    //Logic of getting all the members in our database
    @GetMapping
    public List<MemberResponseDto>
}

package com.srijon.library.controller;

import com.srijon.library.dto.MemberRequestDto;
import com.srijon.library.dto.MemberResponseDto;
import com.srijon.library.entity.Member;
import com.srijon.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<MemberResponseDto> getAllMembers(){

        return memberService.getAllMembers();
    }

    //Logic of getting a particular member using its id
    @GetMapping("/{id}")
    public MemberResponseDto getMemberById(@PathVariable Long id){

        return memberService.getMemberById(id);
    }

    //Logic of updating a member
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto memberRequestDto
    ){

        MemberResponseDto updateMember = memberService.updateMember(id,memberRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateMember);
    }

    //Logic of deleting the member
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){

        memberService.deleteMember(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

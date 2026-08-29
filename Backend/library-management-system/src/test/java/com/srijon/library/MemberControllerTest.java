package com.srijon.library;

import com.srijon.library.controller.MemberController;
import com.srijon.library.dto.Member.MemberRequestDto;
import com.srijon.library.dto.Member.MemberResponseDto;
import com.srijon.library.exception.MemberNotFoundException;
import com.srijon.library.service.MemberService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberService memberService;


    // =========================================================
    // 1. ADD MEMBER - SUCCESS
    // =========================================================

    @Test
    void addMember_shouldReturnCreated() throws Exception {

        MemberRequestDto request = new MemberRequestDto();

        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("9876543210");
        request.setMembershipDate(LocalDate.of(2026, 8, 29));


        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john@example.com");
        response.setPhone("9876543210");
        response.setMembershipDate(LocalDate.of(2026, 8, 29));


        when(memberService.addMember(any(MemberRequestDto.class)))
                .thenReturn(response);


        mockMvc.perform(
                        post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email")
                        .value("john@example.com"))
                .andExpect(jsonPath("$.phone")
                        .value("9876543210"))
                .andExpect(jsonPath("$.membershipDate")
                        .value("2026-08-29"));
    }


    // =========================================================
    // 2. GET ALL MEMBERS - SUCCESS
    // =========================================================

    @Test
    void getAllMembers_shouldReturnMembers() throws Exception {

        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john@example.com");
        response.setPhone("9876543210");
        response.setMembershipDate(LocalDate.of(2026, 8, 29));


        Page<MemberResponseDto> page =
                new PageImpl<>(List.of(response));


        when(memberService.getAllMembers(any(Pageable.class)))
                .thenReturn(page);


        mockMvc.perform(
                        get("/api/members")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name")
                        .value("John Doe"))
                .andExpect(jsonPath("$.content[0].email")
                        .value("john@example.com"))
                .andExpect(jsonPath("$.content[0].phone")
                        .value("9876543210"));
    }


    // =========================================================
    // 3. GET MEMBER BY ID - SUCCESS
    // =========================================================

    @Test
    void getMemberById_shouldReturnMember() throws Exception {

        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john@example.com");
        response.setPhone("9876543210");
        response.setMembershipDate(LocalDate.of(2026, 8, 29));


        when(memberService.getMemberById(1L))
                .thenReturn(response);


        mockMvc.perform(
                        get("/api/members/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("John Doe"))
                .andExpect(jsonPath("$.email")
                        .value("john@example.com"))
                .andExpect(jsonPath("$.phone")
                        .value("9876543210"))
                .andExpect(jsonPath("$.membershipDate")
                        .value("2026-08-29"));
    }


    // =========================================================
    // 4. GET MEMBER BY ID - NOT FOUND
    // =========================================================

    @Test
    void getMemberById_shouldReturnNotFound() throws Exception {

        when(memberService.getMemberById(999L))
                .thenThrow(
                        new MemberNotFoundException(
                                "Member not found with id : 999"
                        )
                );

        mockMvc.perform(
                        get("/api/members/{id}", 999L)
                )
                .andExpect(status().isNotFound());
    }


    // =========================================================
    // 5. UPDATE MEMBER - SUCCESS
    // =========================================================

    @Test
    void updateMember_shouldReturnUpdatedMember() throws Exception {

        MemberRequestDto request = new MemberRequestDto();

        request.setName("John Updated");
        request.setEmail("john.updated@example.com");
        request.setPhone("9999999999");
        request.setMembershipDate(LocalDate.of(2026, 8, 29));


        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Updated");
        response.setEmail("john.updated@example.com");
        response.setPhone("9999999999");
        response.setMembershipDate(LocalDate.of(2026, 8, 29));


        when(memberService.updateMember(
                eq(1L),
                any(MemberRequestDto.class)
        )).thenReturn(response);


        mockMvc.perform(
                        put("/api/members/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("John Updated"))
                .andExpect(jsonPath("$.email")
                        .value("john.updated@example.com"))
                .andExpect(jsonPath("$.phone")
                        .value("9999999999"));
    }


    // =========================================================
    // 6. UPDATE MEMBER - NOT FOUND
    // =========================================================

    @Test
    void updateMember_shouldReturnNotFound() throws Exception {

        MemberRequestDto request = new MemberRequestDto();

        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("9876543210");
        request.setMembershipDate(LocalDate.of(2026, 8, 29));


        when(memberService.updateMember(
                eq(999L),
                any(MemberRequestDto.class)
        )).thenThrow(
                new MemberNotFoundException(
                        "Member not found with id : 999"
                )
        );


        mockMvc.perform(
                        put("/api/members/{id}", 999L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(status().isNotFound());
    }


    // =========================================================
    // 7. DELETE MEMBER - SUCCESS
    // =========================================================

    @Test
    void deleteMember_shouldReturnNoContent() throws Exception {

        doNothing()
                .when(memberService)
                .deleteMember(1L);


        mockMvc.perform(
                        delete("/api/members/{id}", 1L)
                )
                .andExpect(status().isNoContent());


        verify(memberService).deleteMember(1L);
    }


    // =========================================================
    // 8. SEARCH MEMBER - SUCCESS
    // =========================================================

    @Test
    void searchMember_shouldReturnMatchingMembers() throws Exception {

        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john@example.com");
        response.setPhone("9876543210");
        response.setMembershipDate(LocalDate.of(2026, 8, 29));


        Page<MemberResponseDto> page =
                new PageImpl<>(List.of(response));


        when(memberService.searchMember(
                eq("John"),
                any(Pageable.class)
        )).thenReturn(page);


        mockMvc.perform(
                        get("/api/members/search")
                                .param("query", "John")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value("John Doe"))
                .andExpect(jsonPath("$.content[0].email")
                        .value("john@example.com"))
                .andExpect(jsonPath("$.content[0].phone")
                        .value("9876543210"));
    }


    // =========================================================
    // 9. SEARCH MEMBER - NO RESULTS
    // =========================================================

    @Test
    void searchMember_shouldReturnEmptyPage() throws Exception {

        Page<MemberResponseDto> emptyPage =
                Page.empty();


        when(memberService.searchMember(
                eq("xyz"),
                any(Pageable.class)
        )).thenReturn(emptyPage);


        mockMvc.perform(
                        get("/api/members/search")
                                .param("query", "xyz")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}
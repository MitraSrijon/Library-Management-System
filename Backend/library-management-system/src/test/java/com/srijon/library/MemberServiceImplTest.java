package com.srijon.library;

import com.srijon.library.dto.Member.MemberRequestDto;
import com.srijon.library.dto.Member.MemberResponseDto;
import com.srijon.library.entity.Member;
import com.srijon.library.exception.MemberNotFoundException;
import com.srijon.library.mapper.MemberMapper;
import com.srijon.library.repository.MemberRepository;
import com.srijon.library.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;


    // =========================================================
    // 1. ADD MEMBER - SUCCESS
    // =========================================================

    @Test
    void addMember_shouldSaveMemberSuccessfully() {

        // Arrange
        MemberRequestDto request = new MemberRequestDto();

        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("9876543210");
        request.setMembershipDate(LocalDate.of(2026, 8, 29));

        Member member = new Member();

        member.setId(1L);
        member.setName("John Doe");
        member.setEmail("john@example.com");
        member.setPhone("9876543210");
        member.setMembershipDate(LocalDate.of(2026, 8, 29));

        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john@example.com");
        response.setPhone("9876543210");
        response.setMembershipDate(LocalDate.of(2026, 8, 29));

        when(memberMapper.toEntity(request))
                .thenReturn(member);

        when(memberRepository.save(member))
                .thenReturn(member);

        when(memberMapper.toResponseDto(member))
                .thenReturn(response);

        // Act
        MemberResponseDto result =
                memberService.addMember(request);

        // Assert
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("9876543210", result.getPhone());

        verify(memberMapper).toEntity(request);
        verify(memberRepository).save(member);
        verify(memberMapper).toResponseDto(member);
    }


    // =========================================================
    // 2. GET ALL MEMBERS - SUCCESS
    // =========================================================

    @Test
    void getAllMembers_shouldReturnMembersSuccessfully() {

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        Member member = new Member();

        member.setId(1L);
        member.setName("John Doe");

        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");

        Page<Member> memberPage =
                new PageImpl<>(List.of(member));

        when(memberRepository.findAll(pageable))
                .thenReturn(memberPage);

        when(memberMapper.toResponseDto(member))
                .thenReturn(response);

        // Act
        Page<MemberResponseDto> result =
                memberService.getAllMembers(pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(
                "John Doe",
                result.getContent().get(0).getName()
        );

        verify(memberRepository).findAll(pageable);
        verify(memberMapper).toResponseDto(member);
    }


    // =========================================================
    // 3. GET MEMBER BY ID - SUCCESS
    // =========================================================

    @Test
    void getMemberById_shouldReturnMemberSuccessfully() {

        // Arrange
        Long id = 1L;

        Member member = new Member();

        member.setId(id);
        member.setName("John Doe");
        member.setEmail("john@example.com");

        MemberResponseDto response = new MemberResponseDto();

        response.setId(id);
        response.setName("John Doe");
        response.setEmail("john@example.com");

        when(memberRepository.findById(id))
                .thenReturn(Optional.of(member));

        when(memberMapper.toResponseDto(member))
                .thenReturn(response);

        // Act
        MemberResponseDto result =
                memberService.getMemberById(id);

        // Assert
        assertEquals(id, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(
                "john@example.com",
                result.getEmail()
        );

        verify(memberRepository).findById(id);
        verify(memberMapper).toResponseDto(member);
    }


    // =========================================================
    // 4. GET MEMBER BY ID - NOT FOUND
    // =========================================================

    @Test
    void getMemberById_shouldThrowExceptionWhenMemberNotFound() {

        // Arrange
        Long id = 999L;

        when(memberRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        MemberNotFoundException exception = assertThrows(
                MemberNotFoundException.class,
                () -> memberService.getMemberById(id)
        );

        assertEquals(
                "Member not found with id : " + id,
                exception.getMessage()
        );

        verify(memberRepository).findById(id);

        verify(memberMapper, never())
                .toResponseDto(any());
    }


    // =========================================================
    // 5. UPDATE MEMBER - SUCCESS
    // =========================================================

    @Test
    void updateMember_shouldUpdateMemberSuccessfully() {

        // Arrange
        Long id = 1L;

        MemberRequestDto request = new MemberRequestDto();

        request.setName("John Updated");
        request.setEmail("john.updated@example.com");
        request.setPhone("9999999999");
        request.setMembershipDate(
                LocalDate.of(2026, 8, 29)
        );

        Member member = new Member();

        member.setId(id);
        member.setName("John Doe");
        member.setEmail("john@example.com");
        member.setPhone("9876543210");
        member.setMembershipDate(
                LocalDate.of(2026, 8, 1)
        );

        MemberResponseDto response = new MemberResponseDto();

        response.setId(id);
        response.setName("John Updated");
        response.setEmail("john.updated@example.com");
        response.setPhone("9999999999");
        response.setMembershipDate(
                LocalDate.of(2026, 8, 29)
        );

        when(memberRepository.findById(id))
                .thenReturn(Optional.of(member));

        when(memberRepository.save(member))
                .thenReturn(member);

        when(memberMapper.toResponseDto(member))
                .thenReturn(response);

        // Act
        MemberResponseDto result =
                memberService.updateMember(id, request);

        // Assert
        assertEquals(
                "John Updated",
                result.getName()
        );

        assertEquals(
                "john.updated@example.com",
                result.getEmail()
        );

        assertEquals(
                "9999999999",
                result.getPhone()
        );

        assertEquals(
                LocalDate.of(2026, 8, 29),
                result.getMembershipDate()
        );

        verify(memberRepository).findById(id);
        verify(memberRepository).save(member);
        verify(memberMapper).toResponseDto(member);
    }


    // =========================================================
    // 6. UPDATE MEMBER - NOT FOUND
    // =========================================================

    @Test
    void updateMember_shouldThrowExceptionWhenMemberNotFound() {

        // Arrange
        Long id = 999L;

        MemberRequestDto request = new MemberRequestDto();

        request.setName("John Updated");
        request.setEmail("john.updated@example.com");
        request.setPhone("9999999999");
        request.setMembershipDate(
                LocalDate.of(2026, 8, 29)
        );

        when(memberRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        MemberNotFoundException exception = assertThrows(
                MemberNotFoundException.class,
                () -> memberService.updateMember(id, request)
        );

        assertEquals(
                "Member not found with id : " + id,
                exception.getMessage()
        );

        verify(memberRepository).findById(id);

        verify(memberRepository, never())
                .save(any());

        verify(memberMapper, never())
                .toResponseDto(any());
    }


    // =========================================================
    // 7. DELETE MEMBER - SUCCESS
    // =========================================================

    @Test
    void deleteMember_shouldDeleteMemberSuccessfully() {

        // Arrange
        Long id = 1L;

        Member member = new Member();

        member.setId(id);
        member.setName("John Doe");

        when(memberRepository.findById(id))
                .thenReturn(Optional.of(member));

        // Act
        memberService.deleteMember(id);

        // Assert
        verify(memberRepository).findById(id);
        verify(memberRepository).delete(member);
    }


    // =========================================================
    // 8. DELETE MEMBER - NOT FOUND
    // =========================================================

    @Test
    void deleteMember_shouldThrowExceptionWhenMemberNotFound() {

        // Arrange
        Long id = 999L;

        when(memberRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        MemberNotFoundException exception = assertThrows(
                MemberNotFoundException.class,
                () -> memberService.deleteMember(id)
        );

        assertEquals(
                "Member not found with id : " + id,
                exception.getMessage()
        );

        verify(memberRepository).findById(id);

        verify(memberRepository, never())
                .delete(any());
    }


    // =========================================================
    // 9. SEARCH MEMBER - SUCCESS
    // =========================================================

    @Test
    void searchMember_shouldReturnMatchingMembers() {

        // Arrange
        String query = "John";
        Pageable pageable = PageRequest.of(0, 10);

        Member member = new Member();

        member.setId(1L);
        member.setName("John Doe");
        member.setEmail("john@example.com");
        member.setPhone("9876543210");

        MemberResponseDto response = new MemberResponseDto();

        response.setId(1L);
        response.setName("John Doe");
        response.setEmail("john@example.com");
        response.setPhone("9876543210");

        Page<Member> memberPage =
                new PageImpl<>(List.of(member));

        when(memberRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                        query, query, query, pageable
                ))
                .thenReturn(memberPage);

        when(memberMapper.toResponseDto(member))
                .thenReturn(response);

        // Act
        Page<MemberResponseDto> result =
                memberService.searchMember(query, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());

        assertEquals(
                "John Doe",
                result.getContent().get(0).getName()
        );

        verify(memberRepository)
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                        query, query, query, pageable
                );

        verify(memberMapper).toResponseDto(member);
    }


    // =========================================================
    // 10. SEARCH MEMBER - NO RESULTS
    // =========================================================

    @Test
    void searchMember_shouldReturnEmptyPageWhenNoMembersFound() {

        // Arrange
        String query = "xyz";
        Pageable pageable = PageRequest.of(0, 10);

        Page<Member> emptyPage = Page.empty();

        when(memberRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                        query, query, query, pageable
                ))
                .thenReturn(emptyPage);

        // Act
        Page<MemberResponseDto> result =
                memberService.searchMember(query, pageable);

        // Assert
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(memberRepository)
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                        query, query, query, pageable
                );

        verify(memberMapper, never())
                .toResponseDto(any());
    }
}
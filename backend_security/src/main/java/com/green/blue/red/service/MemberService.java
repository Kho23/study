package com.green.blue.red.service;

import com.green.blue.red.domain.Member;
import com.green.blue.red.dto.MemberDto;
import com.green.blue.red.dto.MemberModifyDto;
import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {
    MemberDto getKakaoMember(String accessToken);
    void modifyMember(MemberModifyDto memberModifyDto);

    default MemberDto entityToDto(Member member) {
        MemberDto dto = new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream()
                        .map(i -> i.name()).toList()
        );
        return dto;
    }
//    default Member entityToDto(MemberDto memberDto) {
//        Member member = new MemberDto(
//                memberDto.getEmail(),
//                member.getPw(),
//                member.getNickname(),
//                member.isSocial(),
//                member.getMemberRoleList().stream()
//                        .map(i -> i.name()).toList()
//        );
//        return dto;
//    }
}

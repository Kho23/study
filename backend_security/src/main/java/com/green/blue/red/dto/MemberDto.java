package com.green.blue.red.dto;

import com.green.blue.red.domain.MemberRole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Setter
@Getter
@ToString

public class MemberDto extends User {
    @Id
    private String email;
    private String pw;
    private String nickname;
    private boolean social;
    private List<String> roleNames = new ArrayList<>();

    public MemberDto(String email, String pw, String nickname, boolean social, List<String> roleNames){
        super(email, pw, roleNames.stream().map(i->new SimpleGrantedAuthority("ROLE_"+i)).toList());
        this.email=email;
        this.pw=pw;
        this.social=social;
        this.nickname=nickname;
        this.roleNames=roleNames;
    }
    public Map<String, Object> getClaims(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email",email);
        dataMap.put("pw",pw);
        dataMap.put("nickname",nickname);
        dataMap.put("social",social);
        dataMap.put("roleNames",roleNames);
        return dataMap;
    }



}

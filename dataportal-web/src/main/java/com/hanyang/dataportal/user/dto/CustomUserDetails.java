//package com.hanyang.dataportal.user.dto;
//
//import com.hanyang.dataportal.user.domain.User;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//@Getter
//public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
//
//    private final String name;
//    private final boolean isActive;
//
//    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,User user) {
//        super(username, password, authorities);
//        this.name = user.getName();
//        this.isActive = user.isActive();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.isActive;
//    }
//
//    public String getName() {
//        return name;
//    }
//}

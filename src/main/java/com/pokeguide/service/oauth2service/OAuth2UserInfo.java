package com.pokeguide.service.oauth2service;

import jakarta.servlet.http.HttpServletRequest;

public interface OAuth2UserInfo {

    //공급자 ex) google, facebook
    String getName(); //사용자 이름 ex) 홍길동
    String getEmail();

}
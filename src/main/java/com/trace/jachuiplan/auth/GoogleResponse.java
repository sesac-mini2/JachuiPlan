package com.trace.jachuiplan.auth;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{

    private final Map<String, Object> attributes;

    public GoogleResponse(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString(); // 구글 사용자 고유 ID
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString(); // 이메일
    }


}

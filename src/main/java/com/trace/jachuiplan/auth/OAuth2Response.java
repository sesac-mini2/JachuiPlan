/// 김정은
package com.trace.jachuiplan.auth;

public interface OAuth2Response {
    String getProvider();
    String getProviderId();
    String getEmail();
}

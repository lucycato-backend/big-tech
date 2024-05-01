package org.lucycato.userservice.adapter.out.externalsystem;


import lombok.RequiredArgsConstructor;
import org.lucycato.common.annotation.hexagonal.out.ExternalSystemAdapter;
import org.lucycato.userservice.application.port.out.SocialLoginPort;
import org.lucycato.userservice.application.port.out.result.AppleAccountResult;
import org.lucycato.userservice.application.port.out.result.KakaoAccountResult;
import org.springframework.web.client.RestTemplate;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class SocialLoginExternalSystemAdapter implements SocialLoginPort {

    //TODO: Social Login 구현
    @Override
    public KakaoAccountResult getKakaoAccount(String code) {
        return KakaoAccountResult.builder()
                .name("DO")
                .email("willd88@naver.com")
                .phoneNumber("01087051693")
                .build();
    }

    @Override
    public AppleAccountResult getAppleAccount(String code) {
        return AppleAccountResult.builder()
                .email("willd88@apple.com")
                .fullName("01087051693")
                .build();
    }

}

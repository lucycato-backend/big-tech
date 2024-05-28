package org.lucycato.userservice.adapter.in.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AgreeMarketingTermsRequest {
    private Long appUserId;

    private Boolean isAgreeMarketingTerms;
}

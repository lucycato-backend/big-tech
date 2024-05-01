package org.lucycato.userservice.application.port.out;

import org.lucycato.userservice.application.port.out.result.AppUserMembershipResult;
import org.lucycato.userservice.application.port.out.result.AppUserResult;
import org.lucycato.userservice.domain.enums.AppUserStatus;
import org.lucycato.userservice.domain.enums.MembershipGrade;
import org.lucycato.userservice.domain.enums.SocialStatus;

import java.time.LocalDateTime;

public interface AppUserPort {
    AppUserResult registerAppUser(
            SocialStatus socialStatus,
            String name,
            String email,
            String phoneNumber,
            AppUserStatus status
    );

    AppUserResult modifyAppUserPhoneNumber(
            Long appUserId,
            String phoneNumber
    );

    AppUserMembershipResult registerAppUserMembership(
            Long appUserId,
            MembershipGrade membershipGrade,
            LocalDateTime expiredAt
    );

    AppUserMembershipResult safeRemoveAppUserMembership(
            Long appUserMembershipId
    );
}

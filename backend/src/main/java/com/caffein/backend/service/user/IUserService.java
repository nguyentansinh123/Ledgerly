package com.caffein.backend.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    // void UpdateProfileInfo(ProfileUpdateRequest request, String userId);

    // void changePassword(ChangePasswordRequest request, String userId);

    void deactivateAccount(String userId);

    void reactivateAccount(String userId);

    void deleteAccount(String userId);
}

package com.noron.core.data.request.core;

import com.noron.core.validation.PasswordMatch;
import com.noron.core.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@PasswordMatch
public class UserSignInAndUpdateRequest {
    @NotNull
    @Size(min = 1)
    private String userName;

    @NotNull
    @Size(min = 1)
    private String userAccount;


    @ValidPassword
    private String userPassword;

    @Size(min = 1)
    @NotNull
    private String matchingPassword;

    @Email
    @NotNull
    @Size(min = 1)
    private String userEmail;

    private String userAvatar;

    private boolean isUsing2FA;

    private String authority;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [userName=")
                .append(userName)
                .append(", isUsing2FA=")
                .append(isUsing2FA)
                .append(", userAvatar=")
                .append(userAvatar)
                .append(" , authority=")
                .append(authority);
        return builder.toString();
    }
}

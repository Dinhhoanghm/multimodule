package com.noron.core.validation;

import com.noron.core.data.request.core.UserSignInAndUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {
        //
    }
    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserSignInAndUpdateRequest user = (UserSignInAndUpdateRequest) obj;
        return user.getUserPassword().equals(user.getMatchingPassword());
    }
}

package Inventory.Forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Inventory.Repositories.UsersRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // handled by @NotBlank separately
        }
        return !usersRepository.existsByEmail(email);
    }


}

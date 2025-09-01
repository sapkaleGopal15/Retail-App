package Inventory.Forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Inventory.Repositories.UsersRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueGSTNumberValidator implements ConstraintValidator<UniqueGSTNumber, String> {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean isValid(String shopGSTNumber, ConstraintValidatorContext context) {
        if (shopGSTNumber == null || shopGSTNumber.isEmpty()) {
            return true; // let @NotBlank handle empty case
        }
        return !usersRepository.existsByGstNumber(shopGSTNumber);
    }
}

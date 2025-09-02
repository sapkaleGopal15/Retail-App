package Inventory.Forms;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "First name is required")
    private String fName;

    @NotBlank(message = "Last name is required")
    private String lName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password legth minimum 8 character, at list 1 special character, 1 LowerCase letter, 1 UpperCase latter and 1 number is must")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "[789][0-9]{9}", message = "Invalid Phone number")
    private String contact;

    @NotBlank(message = "shop name is required")
    private String shopName;

    @NotBlank(message = "shop GST number is required")
    @Pattern(regexp = "[0-3]{1}[0-9]{1}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9]{1}[Z]{1}[0-9]{1}", message = "Invalid GST number")
    @UniqueGSTNumber
    private String shopGSTNumber;

    @NotBlank(message = "Shop address is required")
    private String shopAddress;

    @AssertTrue(message = "Accept the Agreement")
    private boolean checkBox;
}

package Inventory.Forms;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductForm {

    @NotBlank(message = "Product name is Required")
    private String name;

    @NotBlank(message = "Product brand name is required")
    private String company;

    @NotNull(message = "Product price is Required")
    @Positive(message = "Price must be grether than 0")
    private Integer price;

    private String description;

    @NotNull(message = "Product quantity is Required")
    @Positive(message = "quantity must be grether than 0")
    private Integer quantity;

    @NotNull(message = "Manufacturing Date must be required")
    @PastOrPresent(message = "Start date cannot be in the future")
    private LocalDate manufacturingDate;

    @NotNull(message = "Expiry Date must be required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

}

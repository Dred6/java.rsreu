package ru.rsreu.medicinalPlants;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Order {

    private Long id;
    private Date placedAt;

    @NotBlank(message="Имя обязательное поле")
    private String deliveryName;

//    @NotBlank(message="State is required")
//    private String state;

//    @NotBlank(message="Zip code is required")
//    private String zip;

    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message="Формат должен быть MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Неправильный CVV")
    private String ccCVV;

    private List<Ticket> tickets = new ArrayList<>();

    public void addDesign(Ticket design) {
        this.tickets.add(design);
    }
}

package ru.rsreu.medicinalPlants;

import lombok.Data;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Ticket {

    private Long id;
    private Date createdAt;

    @NotNull
    private String name;

    @Size(min=1, message="Вы должны выбрать как мининимум один.")
    private List<Gears> gears;

}

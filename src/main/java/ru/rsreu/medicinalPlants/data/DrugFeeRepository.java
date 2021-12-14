package ru.rsreu.medicinalPlants.data;

import ru.rsreu.medicinalPlants.Ticket;

public interface DrugFeeRepository {

    Ticket save(Ticket design);
}

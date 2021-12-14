package ru.rsreu.medicinalPlants.data;

import ru.rsreu.medicinalPlants.Gears;

public interface ConstituentsRepository {

    Iterable<Gears> findAll();

    Gears findById(String id);

    Gears save(Gears constituents);
}

package ru.rsreu.medicinalPlants.data;

import ru.rsreu.medicinalPlants.Order;

public interface OrderRepository {

    Order save(Order order);
}

package ru.rsreu.medicinalPlants.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.rsreu.medicinalPlants.Gears;
import ru.rsreu.medicinalPlants.data.ConstituentsRepository;

@Component
public class ConstituentsByIdConverter implements Converter<String, Gears>  {

    private ConstituentsRepository constituentsRepo;

    @Autowired
    public ConstituentsByIdConverter(ConstituentsRepository constituentsRepo){
        this.constituentsRepo = constituentsRepo;
    }

    @Override
    public Gears convert(String id){
        return constituentsRepo.findById(id);
    }
}

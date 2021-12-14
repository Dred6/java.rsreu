package ru.rsreu.medicinalPlants.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.rsreu.medicinalPlants.Gears;
import ru.rsreu.medicinalPlants.Gears.Type;
import ru.rsreu.medicinalPlants.Ticket;
import ru.rsreu.medicinalPlants.Order;
import ru.rsreu.medicinalPlants.data.ConstituentsRepository;
import ru.rsreu.medicinalPlants.data.DrugFeeRepository;

import javax.validation.Valid;

//@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignDrugFeeController {

    private final ConstituentsRepository constituentsRepo;

    private DrugFeeRepository designRepo;

    @Autowired
    public DesignDrugFeeController(
            ConstituentsRepository constituentsRepo,
            DrugFeeRepository designRepo){
        this.constituentsRepo = constituentsRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "ticket")
    public Ticket drugFee(){
        return new Ticket();
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Gears> gears = new ArrayList<>();
        constituentsRepo.findAll().forEach(i -> gears.add(i));

        Type[] types = Gears.Type.values();
        for (Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(gears,type));
        }
        return "design";

//        model.addAttribute("design", new DrugFee());
//        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Ticket design, Errors errors,
                                @ModelAttribute Order order){
        if (errors.hasErrors()){
            return "design";
        }

        Ticket saved = designRepo.save(design);
        order.addDesign(saved);
        //log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }

    private List<Gears> filterByType(
            List<Gears> constituents, Type type) {
        return constituents
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}

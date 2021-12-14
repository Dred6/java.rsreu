package ru.rsreu.medicinalPlants.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.rsreu.medicinalPlants.Ticket;
import ru.rsreu.medicinalPlants.Order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderDrugFeeInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc){
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Ticket_Order")
                .usingGeneratedKeyColumns("id");

        this.orderDrugFeeInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Ticket_Order_Ticket");

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order){
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Ticket> tickets = order.getTickets();
        for (Ticket ticket : tickets){
            saveDrugFeeToOrder(ticket, orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order) {
        //@SuppressWarnings("unchecked")
        Map<String, Object> values =
                objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());

        long orderId =
                orderInserter
                        .executeAndReturnKey(values)
                        .longValue();
        return orderId;
    }

    private void saveDrugFeeToOrder(Ticket drugFee, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("ticketOrder", orderId);
        values.put("ticket", drugFee.getId());
        orderDrugFeeInserter.execute(values);
    }
}

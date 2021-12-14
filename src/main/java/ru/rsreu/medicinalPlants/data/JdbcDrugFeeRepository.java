package ru.rsreu.medicinalPlants.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.rsreu.medicinalPlants.Gears;
import ru.rsreu.medicinalPlants.Ticket;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcDrugFeeRepository implements DrugFeeRepository{

    private JdbcTemplate jdbc;

    public JdbcDrugFeeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Ticket save(Ticket ticket) {
        long drugFeeId = saveDrugFeeInfo(ticket);
        ticket.setId(drugFeeId);
        for (Gears gears : ticket.getGears()) {
            saveConstituentsToDrugFee(gears, drugFeeId);
        }

        return ticket;
    }

    private long saveDrugFeeInfo(Ticket ticket) {
        ticket.setCreatedAt(new Date());
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "insert into Ticket (name, createdAt) values (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP
                ).newPreparedStatementCreator(
                        Arrays.asList(
                                ticket.getName(),
                                new Timestamp(ticket.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return 1;
    }

    private void saveConstituentsToDrugFee(
            Gears constituents, long drugFeeId) {
        jdbc.update(
                "insert into Ticket_Gears (ticket, gears) " +
                        "values (?, ?)",
                drugFeeId, constituents.getId());
    }
}

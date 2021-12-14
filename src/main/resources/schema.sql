create table if not exists Gears (
    id varchar(50) not null,
    name varchar(50) not null,
    type varchar(50) not null
    );

create table if not exists Ticket (
    id identity,
    name varchar(50) not null,
    createdAt timestamp not null
    );

create table if not exists Ticket_Gears (
    ticket bigint not null,
    gears varchar(50) not null
    );

alter table Ticket_Gears
    add foreign key (gears) references Gears(id);
alter table Ticket_Gears
    add foreign key (ticket) references Ticket(id);

create table if not exists Ticket_Order (
                                          id identity,
                                          deliveryName varchar(50) not null,
--     deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    placedAt timestamp not null
    );

create table if not exists Ticket_Order_Ticket(
    ticket_Order bigint,
    ticket bigint not null
);

alter table Ticket_Order_Ticket
    add foreign key (ticket_Order) references Ticket_Order(id);
alter table Ticket_Order_Ticket
    add foreign key (ticket) references Ticket(id);

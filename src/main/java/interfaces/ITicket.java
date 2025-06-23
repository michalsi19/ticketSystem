package interfaces;

import enums.Status;
import enums.TicketType;

public interface ITicket {
    int getId();
    String getDescription();
    String getResolution();
    TicketType getTicketType();
    Status getStatus();
}
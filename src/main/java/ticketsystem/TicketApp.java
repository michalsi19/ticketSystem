package ticketsystem;

import interfaces.*;
import manager.TicketManager;
import manager.TicketSeverityStatisticsManager;

public class TicketApp {
    public static void main(String[] args) {
        ITicketManager manager = TicketManager.getInstance();
        TicketSeverityStatisticsManager stats = new TicketSeverityStatisticsManager();

        TicketService ticketService = new TicketService(manager, stats);

        ticketService.generateTickets(1000);
        ticketService.printStatistics();
    }
}
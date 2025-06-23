package ticketsystem;

import interfaces.ITicket;
import manager.TicketManager;
import manager.TicketSeverityStatisticsManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceTest {

    private TicketManager ticketManager;
    private TicketSeverityStatisticsManager statisticsManager;
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketManager = TicketManager.getInstance();
        ticketManager.clear(); // Reset state before each test

        statisticsManager = new TicketSeverityStatisticsManager();
        ticketService = new TicketService(ticketManager, statisticsManager);
    }

    @Test
    void testGenerateTickets_addsExpectedNumberOfTickets() {
        int amount = 50;
        ticketService.generateTickets(amount);

        Collection<ITicket> tickets = ticketManager.getTickets();
        assertEquals(amount, tickets.size(), "Number of tickets in the manager should match amount generated");
    }

    @Test
    void testGenerateTickets_updatesSeverityCountsCorrectly() {
        int amount = 30;
        ticketService.generateTickets(amount);

        int totalSeverities = statisticsManager.getSeverityCount().values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        assertEquals(amount, totalSeverities, "Sum of severity counts should match the number of generated tickets");
    }

    @Test
    void testGenerateTickets_openTicketsWithCVE_inExpectedRange() {
        int amount = 100;
        ticketService.generateTickets(amount);

        int openWithCve = statisticsManager.getOpenTicketsWithCve().get();
        assertTrue(openWithCve >= 0 && openWithCve <= amount, "Open CVE ticket count must be within [0, amount]");
    }

    @Test
    void testGenerateTickets_closedErrorTickets_inExpectedRange() {
        int amount = 100;
        ticketService.generateTickets(amount);

        int closedHighSeverity = statisticsManager.getClosedHighSeverityTickets().get();
        assertTrue(closedHighSeverity >= 0 && closedHighSeverity <= amount, "Closed ERROR ticket count must be within [0, amount]");
    }

    @Test
    void testPrintStatistics_doesNotThrow() {
        ticketService.generateTickets(10);
        assertDoesNotThrow(() -> ticketService.printStatistics());
    }
}

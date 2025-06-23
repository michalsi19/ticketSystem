package ticketsystem;

import interfaces.EnhancedTicket;
import interfaces.ITicketManager;
import interfaces.ITicketSeverity;
import manager.TicketSeverityStatisticsManager;
import model.ConfigurationTicket;
import model.SecurityTicket;
import model.VendorBestPracticeTicket;
import enums.Status;

import java.util.Random;

public class TicketService {

    // The ticket manager responsible for storing and managing tickets.
    private final ITicketManager ticketManager;

    // The statistics manager responsible for calculating ticket severity and CVE statistics.
    private final TicketSeverityStatisticsManager statisticsManager;

    // Random instance used to generate random ticket properties.
    private final Random random = new Random();

    // Predefined CVE strings used for tickets related to vulnerabilities.
    private final String[] cves = {"CVE-1111", "CVE-2222", "CVE-3333"};

    /**
     * Constructs the TicketService with the given ticket manager and statistics manager.
     *
     * @param ticketManager the ticket manager to add tickets to
     * @param statisticsManager the statistics manager to update statistics
     */
    public TicketService(ITicketManager ticketManager, TicketSeverityStatisticsManager statisticsManager) {
        this.ticketManager = ticketManager;
        this.statisticsManager = statisticsManager;
    }

    /**
     * Generates a specified amount of random tickets,
     * adds them to the ticket manager, and updates the statistics manager.
     *
     * @param amount the number of tickets to generate
     */
    public void generateTickets(int amount) {
        for (int i = 0; i < amount; i++) {
            int id = ticketManager.generateNewId();
            Status status = random.nextBoolean() ? Status.OPEN : Status.CLOSED;
            ITicketSeverity.Severity severity = ITicketSeverity.Severity.values()[random.nextInt(3)];
            String description = "Issue #" + id;
            String resolution = "Resolution for issue #" + id;

            EnhancedTicket ticket = createRandomTicket(id, status, severity, description, resolution);
            ticketManager.addTicket(ticket);
            statisticsManager.addTicketForAnalyzing(ticket);
        }
    }

    /**
     * Prints statistics including severity distribution, CVE counts,
     * number of open tickets with CVE, and number of closed high severity tickets.
     */
    public void printStatistics() {
        System.out.println(statisticsManager.calcSeverityStatistics());
        System.out.println(statisticsManager.calcCVEStatistics());
        System.out.println("Open tickets with CVE: " + statisticsManager.getOpenTicketsWithCve());
        System.out.println("Closed tickets with severity ERROR: " + statisticsManager.getClosedHighSeverityTickets());
    }

    /**
     * Creates a random ticket of one of three types: SecurityTicket, ConfigurationTicket,
     * or VendorBestPracticeTicket, initialized with the given properties.
     *
     * @param id the unique ticket ID
     * @param status the status of the ticket (OPEN or CLOSED)
     * @param severity the severity level of the ticket
     * @param description the description of the issue
     * @param resolution the resolution description for the issue
     * @return a new instance of EnhancedTicket
     * @throws IllegalArgumentException if the random type is outside the expected range
     */
    private EnhancedTicket createRandomTicket(int id, Status status, ITicketSeverity.Severity severity,
                                              String description, String resolution) {
        return switch (random.nextInt(3)) {
            case 0 -> new SecurityTicket(id, description, resolution, severity, cves[random.nextInt(cves.length)], status);
            case 1 -> new ConfigurationTicket(id, description, resolution, severity, status);
            case 2 -> new VendorBestPracticeTicket(id, description, resolution, severity, status, cves[random.nextInt(cves.length)]);
            default -> throw new IllegalArgumentException("Unhandled ticket type");
        };
    }
}
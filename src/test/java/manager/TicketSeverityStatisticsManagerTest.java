package manager;

import enums.Status;
import interfaces.ITicketSeverity;
import interfaces.EnhancedTicket;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketSeverityStatisticsManagerTest {

    private TicketSeverityStatisticsManager stats;

    @BeforeEach
    public void setUp() {
        stats = new TicketSeverityStatisticsManager();
    }

    @Test
    public void testAddTicketForSeverity() {
        EnhancedTicket ticket = new ConfigurationTicket(
                1, "desc", "res",
                ITicketSeverity.Severity.WARNING,
                Status.OPEN
        );

        stats.addTicketForSeverity(ticket);

        String result = stats.calcSeverityStatistics();
        assertTrue(result.contains("WARNING: 1"));
    }

    @Test
    public void testAddTicketForCVE() {
        EnhancedTicket ticket = new SecurityTicket(
                2, "desc", "res",
                ITicketSeverity.Severity.INFORMATION,
                "CVE-1234",
                Status.OPEN
        );

        stats.addTicketForCVE(ticket);

        String result = stats.calcCVEStatistics();
        assertTrue(result.contains("CVE-1234: 1"));
    }

    @Test
    public void testAddTicketForAnalyzing_OpenCveIncreases() {
        EnhancedTicket ticket = new SecurityTicket(
                3, "desc", "res",
                ITicketSeverity.Severity.ERROR,
                "CVE-5678",
                Status.OPEN
        );

        stats.addTicketForAnalyzing(ticket);
        assertEquals(1, stats.getOpenTicketsWithCve());
    }

    @Test
    public void testAddTicketForAnalyzing_ClosedHighSeverityIncreases() {
        EnhancedTicket ticket = new ConfigurationTicket(
                4, "desc", "res",
                ITicketSeverity.Severity.ERROR,
                Status.CLOSED
        );

        stats.addTicketForAnalyzing(ticket);
        assertEquals(1, stats.getClosedHighSeverityTickets());
    }

    @Test
    public void testAddMultipleTickets() {
        EnhancedTicket t1 = new ConfigurationTicket(5, "a", "b",
                ITicketSeverity.Severity.ERROR, Status.CLOSED);

        EnhancedTicket t2 = new SecurityTicket(6, "c", "d",
                ITicketSeverity.Severity.ERROR, "CVE-0001", Status.OPEN);

        EnhancedTicket t3 = new VendorBestPracticeTicket(7, "e", "f",
                ITicketSeverity.Severity.INFORMATION, Status.OPEN, "CVE-0002");

        stats.addTicketForAnalyzing(t1);
        stats.addTicketForAnalyzing(t2);
        stats.addTicketForAnalyzing(t3);

        assertEquals(1, stats.getClosedHighSeverityTickets());
        assertEquals(2, stats.getOpenTicketsWithCve());

        String cveStats = stats.calcCVEStatistics();
        assertTrue(cveStats.contains("CVE-0001: 1"));
        assertTrue(cveStats.contains("CVE-0002: 1"));

        String severityStats = stats.calcSeverityStatistics();
        assertTrue(severityStats.contains("ERROR: 2"));
        assertTrue(severityStats.contains("INFORMATION: 1"));
    }
}

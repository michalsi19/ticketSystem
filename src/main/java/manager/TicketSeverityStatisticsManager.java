package manager;

import interfaces.ITicketCVE;
import interfaces.ITicketSeverity;
import interfaces.ITicketSeverityStatisticsManager;
import interfaces.EnhancedTicket;
import lombok.Data;
import enums.Status;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages and calculates statistics related to ticket severities and CVEs.
 * Tracks:
 * - Number of tickets per severity level
 * - Number of tickets per CVE ID
 * - Number of open tickets related to CVEs
 * - Number of closed high-severity tickets
 * <p>
 * Thread-safe implementation using concurrent data structures.
 */
@Data
public class TicketSeverityStatisticsManager implements ITicketSeverityStatisticsManager {

    // Map storing the count of tickets for each severity level.
    private final Map<ITicketSeverity.Severity, Integer> severityCount = new ConcurrentHashMap<>();

    // Map storing the count of tickets per CVE identifier.
    private final Map<String, Integer> cveCount = new ConcurrentHashMap<>();

    // Counter for open tickets that are related to a CVE.
    private AtomicInteger openTicketsWithCve = new AtomicInteger(0);

    // Counter for closed tickets with high severity (ERROR level).
    private AtomicInteger closedHighSeverityTickets = new AtomicInteger(0);

    /**
     * Constructor initializes the severity count map with 0 for each severity level.
     */
    public TicketSeverityStatisticsManager() {
        for (ITicketSeverity.Severity severity : ITicketSeverity.Severity.values()) {
            severityCount.put(severity, 0);
        }
    }

    /**
     * Adds a ticket to the severity statistics.
     *
     * @param ticket the ticket to process
     */
    @Override
    public void addTicketForSeverity(ITicketSeverity ticket) {
        if (ticket == null || ticket.getSeverity() == null) return;
        severityCount.merge(ticket.getSeverity(), 1, Integer::sum);
    }

    /**
     * Adds a ticket to the CVE statistics if the CVE is not empty.
     *
     * @param ticket the ticket to process
     */
    @Override
    public void addTicketForCVE(ITicketCVE ticket) {
        if (ticket == null) return;
        String cve = ticket.getCVE();
        if (StringUtils.isNotEmpty(cve)) {
            cveCount.merge(cve, 1, Integer::sum);
        }
    }

    /**
     * Analyzes the ticket to update severity, CVE, and status-based statistics.
     *
     * @param ticket the enhanced ticket to analyze
     */
    @Override
    public void addTicketForAnalyzing(EnhancedTicket ticket) {
        if (ticket == null) return;

        addTicketForSeverity(ticket);

        if (isClosedHighSeverity(ticket)) {
            closedHighSeverityTickets.incrementAndGet();
        }

        if (ticket.getTicketType() != null && ticket.getTicketType().getIsCVE()) {
            addTicketForCVE(ticket);

            if (ticket.getStatus() == Status.OPEN) {
                openTicketsWithCve.incrementAndGet();
            }
        }
    }

    /**
     * Checks if a ticket is closed and has high severity (ERROR).
     *
     * @param ticket the ticket to check
     * @return true if the ticket is closed and has ERROR severity
     */
    private boolean isClosedHighSeverity(EnhancedTicket ticket) {
        return Status.CLOSED == ticket.getStatus()
                && ITicketSeverity.Severity.ERROR == ticket.getSeverity();
    }

    /**
     * Returns a formatted string of severity statistics.
     *
     * @return string with severity counts
     */
    @Override
    public String calcSeverityStatistics() {
        Map<ITicketSeverity.Severity, Integer> snapshot = new EnumMap<>(severityCount);
        StringBuilder sb = new StringBuilder("Severity Statistics:\n");
        for (ITicketSeverity.Severity severity : ITicketSeverity.Severity.values()) {
            sb.append(severity).append(": ")
                    .append(snapshot.getOrDefault(severity, 0))
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a formatted string of CVE statistics.
     *
     * @return string with CVE counts
     */
    @Override
    public String calcCVEStatistics() {
        StringBuilder sb = new StringBuilder("CVE Statistics:\n");

        // snapshot of current state
        Map<String, Integer> snapshot = new HashMap<>(cveCount);

        snapshot.forEach((cve, count) ->
                sb.append(cve).append(": ").append(count).append("\n")
        );

        return sb.toString();
    }
}
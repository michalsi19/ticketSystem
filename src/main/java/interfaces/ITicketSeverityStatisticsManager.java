package interfaces;

public interface ITicketSeverityStatisticsManager {
    void addTicketForSeverity(ITicketSeverity ticket);

    void addTicketForCVE(ITicketCVE ticket);

    void addTicketForAnalyzing(EnhancedTicket ticket);

    String calcSeverityStatistics();

    String calcCVEStatistics();
}
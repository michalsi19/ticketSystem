package interfaces;

public interface ITicketSeverity {
    enum Severity{
        ERROR, WARNING, INFORMATION;
    }
    Severity getSeverity();
}
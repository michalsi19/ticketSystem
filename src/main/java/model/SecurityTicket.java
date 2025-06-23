package model;

import enums.Status;
import enums.TicketType;
import interfaces.EnhancedTicket;

public class SecurityTicket implements EnhancedTicket {

    private final int id;
    private final String description;
    private final String resolution;
    private final Severity severity;
    private final String cve;
    private final Status status;

    public SecurityTicket(int id, String description, String resolution, Severity severity, String cve, Status status) {
        this.id = id;
        this.description = description;
        this.resolution = resolution;
        this.severity = severity;
        this.cve = cve;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getResolution() {
        return resolution;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.SECURITY;
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public String getCVE() {
        return cve;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}

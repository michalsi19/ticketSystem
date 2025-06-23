package model;

import enums.Status;
import enums.TicketType;
import interfaces.ITicketSeverity;
import interfaces.EnhancedTicket;

public class ConfigurationTicket implements EnhancedTicket {

    private final int id;
    private final String description;
    private final String resolution;
    private final ITicketSeverity.Severity severity;
    private final Status status;

    public ConfigurationTicket(int id, String description, String resolution, ITicketSeverity.Severity severity, Status status) {
        this.id = id;
        this.description = description;
        this.resolution = resolution;
        this.severity = severity;
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
        return TicketType.CONFIGURATION;
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getCVE() {
        return null;
    }
}

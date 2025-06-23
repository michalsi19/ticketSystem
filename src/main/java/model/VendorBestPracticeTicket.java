package model;

import enums.Status;
import enums.TicketType;
import interfaces.EnhancedTicket;

public class VendorBestPracticeTicket implements EnhancedTicket {

    private final int id;
    private final String description;
    private final String resolution;
    private final Severity severity;
    private final Status status;
    private final String cve;

    public VendorBestPracticeTicket(int id, String description, String resolution, Severity severity, Status status, String cve) {
        this.id = id;
        this.description = description;
        this.resolution = resolution;
        this.severity = severity;
        this.status = status;
        this.cve = cve;
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
        return TicketType.VENDOR;
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
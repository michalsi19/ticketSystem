package enums;

public enum TicketType {

    CONFIGURATION(false), SECURITY(true), VENDOR(true);

    private final boolean isCVE;

    TicketType(boolean isCVE) {
        this.isCVE = isCVE;
    }

    public boolean getIsCVE(){
        return this.isCVE;
    }
}

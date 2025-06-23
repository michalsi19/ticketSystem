package manager;

import interfaces.ITicket;
import interfaces.ITicketManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton class that manages tickets in a thread-safe manner.
 * Provides functionality to add, remove, retrieve, and list tickets.
 * Each ticket is assigned a unique ID using an atomic counter.
 */
public class TicketManager implements ITicketManager {

    // Singleton instance
    private static final TicketManager instance = new TicketManager();

    // Thread-safe map to store tickets by their ID
    private final Map<Integer, ITicket> ticketMap = new ConcurrentHashMap<>();

    // Atomic counter to generate unique ticket IDs
    private final AtomicInteger idSequence = new AtomicInteger(1);

    /**
     * Private constructor to enforce singleton pattern.
     */
    private TicketManager() {
    }

    /**
     * Returns the singleton instance of TicketManager.
     *
     * @return the TicketManager instance
     */
    public static TicketManager getInstance() {
        return instance;
    }

    /**
     * Adds a ticket to the manager.
     *
     * @param ticket the ticket to add
     */
    @Override
    public void addTicket(ITicket ticket) {
        ticketMap.put(ticket.getId(), ticket);
    }

    /**
     * Removes a ticket from the manager.
     *
     * @param ticket the ticket to remove
     */
    @Override
    public void removeTicket(ITicket ticket) {
        ticketMap.remove(ticket.getId());
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id the ID of the ticket
     * @return the ticket with the specified ID, or null if not found
     */
    @Override
    public ITicket getTicket(int id) {
        return ticketMap.get(id);
    }

    /**
     * Returns a collection of all tickets currently managed.
     *
     * @return a collection of all tickets
     */
    @Override
    public Collection<ITicket> getTickets() {
        return ticketMap.values();
    }

    /**
     * Generates a new unique ID for a ticket.
     *
     * @return a new unique ID
     */
    public int generateNewId() {
        return idSequence.incrementAndGet();
    }

    /**
     * Clears all stored tickets and resets the ID counter.
     * Mainly used for testing or reinitialization.
     */
    public void clear() {
        ticketMap.clear();
        idSequence.set(1);
    }
}
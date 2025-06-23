package manager;

import interfaces.ITicket;
import model.ConfigurationTicket;
import enums.Status;
import interfaces.ITicketSeverity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class TicketManagerTest {

    private TicketManager manager;

    @BeforeEach
    public void setUp() {
        manager = TicketManager.getInstance();
        manager.clear();
    }

    @Test
    public void testGenerateNewIdIncrementsCorrectly() {
        int firstId = manager.generateNewId();
        int secondId = manager.generateNewId();
        assertEquals(firstId + 1, secondId);
    }

    @Test
    public void testAddAndRetrieveTicket() {
        int id = manager.generateNewId();
        ITicket ticket = new ConfigurationTicket(id, "desc", "res", ITicketSeverity.Severity.WARNING, Status.OPEN);
        manager.addTicket(ticket);

        ITicket retrieved = manager.getTicket(id);
        assertNotNull(retrieved);
        assertEquals("desc", retrieved.getDescription());
    }

    @Test
    public void testRemoveTicket() {
        int id = manager.generateNewId();
        ITicket ticket = new ConfigurationTicket(id, "desc", "res", ITicketSeverity.Severity.WARNING, Status.OPEN);
        manager.addTicket(ticket);
        assertEquals(1, manager.getTickets().size());

        manager.removeTicket(ticket);
        assertNull(manager.getTicket(id));
        assertTrue(manager.getTickets().isEmpty());
    }

    @Test
    public void testGetTicketsReturnsAll() {
        ITicket t1 = new ConfigurationTicket(manager.generateNewId(), "t1", "r1", ITicketSeverity.Severity.ERROR, Status.OPEN);
        ITicket t2 = new ConfigurationTicket(manager.generateNewId(), "t2", "r2", ITicketSeverity.Severity.INFORMATION, Status.CLOSED);

        manager.addTicket(t1);
        manager.addTicket(t2);

        Collection<ITicket> all = manager.getTickets();
        assertEquals(2, all.size());
        assertTrue(all.contains(t1));
        assertTrue(all.contains(t2));
    }

    @Test
    public void testClearResetsManager() {
        ITicket t1 = new ConfigurationTicket(manager.generateNewId(), "t1", "r1", ITicketSeverity.Severity.ERROR, Status.OPEN);
        manager.addTicket(t1);
        assertFalse(manager.getTickets().isEmpty());

        manager.clear();
        assertTrue(manager.getTickets().isEmpty());

        int newId = manager.generateNewId();
        assertEquals(1, newId, "After clear, ID sequence should reset to 1");
    }
}

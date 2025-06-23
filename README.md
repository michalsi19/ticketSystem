# Ticket Management System

A Java-based ticketing system designed to create, manage, and analyze tickets with various severity levels and CVE (Common Vulnerabilities and Exposures) tracking.  
The system supports multiple ticket types, maintains unique ticket IDs, and generates detailed severity and CVE statistics.

---

## 🚀 Features

- Supports three ticket types:
    - Security (can have CVE)
    - Configuration (no CVE)
    - Vendor Best Practice (can have CVE)
- Centralized singleton ticket manager ensuring unique incremental ticket IDs across the system.
- Thread-safe statistics manager for tracking:
    - Number of tickets by severity (ERROR, WARNING, INFORMATION)
    - Number of tickets per CVE
    - Counts of open tickets with CVE
    - Counts of closed tickets with high severity (ERROR)
- Generates 1000 random tickets for demonstration and statistical reporting.
- Simple console output of aggregated ticket statistics.

---

## 📁 Project Structure

src/main/java/ticketsystem/  
├── enums/  
│   ├── Status.java               # Ticket status enumeration (OPEN, CLOSED)  
│   └── TicketType.java           # Ticket type enumeration with CVE flag  
├── interfaces/  
│   ├── EnhancedTicket.java       # Composite ticket interface  
│   ├── ITicket.java              # Base ticket interface  
│   ├── ITicketCVE.java           # Interface for CVE support  
│   ├── ITicketManager.java       # Interface for ticket manager  
│   ├── ITicketSeverity.java      # Interface for ticket severity  
│   └── ITicketSeverityStatisticsManager.java  # Interface for statistics manager  
├── manager/  
│   ├── TicketManager.java        # Singleton class for managing tickets  
│   └── TicketSeverityStatisticsManager.java    # Thread-safe statistics manager  
├── model/  
│   ├── SecurityTicket.java       # Security ticket implementation  
│   ├── ConfigurationTicket.java  # Configuration ticket implementation  
│   └── VendorBestPracticeTicket.java  # Vendor best practice ticket implementation  
└── ticketsystem/  
└── TicketService.java        # Service to generate tickets and print statistics

---

## 🔧 Setup & Requirements

- Java 8 or higher
- Apache Commons Lang (for StringUtils)
- Build with Maven or Gradle

---

## 🛠 Running the Application

- Instantiate `TicketService` with the singleton `TicketManager` and `TicketSeverityStatisticsManager`.
- Use `generateTickets(int amount)` to create random tickets.
- Use `printStatistics()` to display severity and CVE related statistics in the console.

Example usage:

```java
TicketService ticketService = new TicketService(TicketManager.getInstance(), new TicketSeverityStatisticsManager());
ticketService.generateTickets(1000);
ticketService.printStatistics();
```


## ⚙️ Key Components

### TicketManager  
Singleton manager responsible for storing tickets with unique incremental IDs. Thread-safe implementation using `ConcurrentHashMap` and `AtomicInteger`.

### TicketSeverityStatisticsManager  
Thread-safe manager collecting statistics for ticket severities, CVE counts, open tickets with CVE, and closed high severity tickets.

### TicketService  
Generates random tickets across different types and severities, updates statistics, and prints summary reports.

---

## 📈 Design Considerations

- Single running manager instance ensures centralized ticket management.  
- Thread-safe collections and atomic counters maintain data integrity in concurrent environments.  
- Separation of concerns: Ticket management and statistics handled in dedicated classes.  
- Flexible design via interfaces to easily extend or modify ticket types and statistics logic.

---

## 🙋‍♀️ Contribution & Support

- Contributions are welcome via pull requests.  
- Please write unit tests for new features.  
- Follow Java coding best practices and project style.  
- For questions, feel free to reach out!

---

## 📚 References

- Java Concurrency Utilities  
- Apache Commons Lang  
- Design Patterns: Singleton, Interface Segregation

---

## 📞 Contact

**Michal Singer – Backend Developer**  
Open to collaborations and inquiries!

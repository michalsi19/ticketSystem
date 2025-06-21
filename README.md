# continuity-software-assignment

# Backend Developer Hands-On Project  
*Continuity Software – June 2025*

---

## Project Overview

This repository contains the solution for the Backend Developer hands-on project assigned by Continuity Software.  
The project is implemented in Java 8+, making extensive use of Java Streams and following best practices in code readability, design, efficiency, and integration.

---

## Project Structure and Content

### 1. REST API Data Collector

- Connects to [JSONPlaceholder](https://jsonplaceholder.typicode.com) to fetch data on users, todos, posts, comments, albums, and photos.
- Implements:
  - Summary of each user’s uncompleted tasks (todos).
  - Retrieval of uncompleted tasks by user ID.
  - Summary of each user’s posts with emails of commenters (excluding posts without comments).
  - Retrieval of albums of a user that contain more photos than a specified threshold.

### 2. Ticketing System Design

- Implements a ticket management system with three ticket types: Security, Configuration, and Vendor Best Practice.
- Provides a singleton `TicketManager` managing ticket creation and storage with unique incremental IDs.
- Includes a `TicketSeverityStatisticsManager` that:
  - Computes statistics by ticket severity.
  - Computes statistics by CVE.
- Generates 1000 random tickets and prints statistics:
  - Number of open tickets with CVEs.
  - Number of closed tickets with high severity.

### 3. IT Research Task

- A comparative analysis of three disaster recovery technologies:
  - VMware Site Recovery Manager (SRM)
  - Zerto
  - Dell EMC RecoverPoint for VMs
- Includes explanations of architecture, replication methods, RPO/RTO capabilities, orchestration features, and typical use cases.

---

## How to Build and Run

1. Clone the repository:
   ```bash
   git clone https://github.com/YourUsername/backend-developer-assignment.git

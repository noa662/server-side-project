# Inquiry Management System - Java


## Overview
A system for managing customer/user inquiries, allowing organized receiving, handling, and tracking of different inquiry types (requests, questions, complaints) with an up-to-date status for each inquiry.  
The system includes a Console interface and server-side processing, with an option for a personal agent to follow each inquiry until resolution.


## Benefits for the Client
- Full control and oversight of all received inquiries
- Tracking inquiry status (opened, handled, canceled, archived)
- Quick and organized response based on inquiry type
- Centralized documentation of all customer interactions
- Smart filtering and reporting by inquiry type, status, date, and more


## Inquiry Types
1. Request (e.g., "Can I extend my subscription?")
2. Question (e.g., "How do I operate the system?")
3. Complaint (e.g., "The service did not work for me yesterday")


## Inquiry Management Stages
- Opening an inquiry by a customer or user
- Viewing and monitoring all inquiries by the support team
- Handling the inquiry by an agent, marking it as "handled"
- Canceling an inquiry (in case of self-resolution or irrelevance)
- Archiving handled inquiries for documentation purposes


## Upgrade with Personal Agent
- Assigning a unique agent to each inquiry who follows it from start to finish
- Clear responsibility division and transparent managerial oversight
- Improved customer service experience and internal efficiency


## Key Features (Server-side)
- Creating inquiries
- Retrieving inquiry status (open, canceled, handled, archived)
- Viewing all inquiries in the system
- Canceling inquiries and moving them to the archive
- Statistics on inquiry volume by time periods
- Agent management (add, remove, save, and load from files)
- Automatic assignment of inquiries to available agents
- "My inquiries" list for agents
- Background inquiry handling simulation
- Full transparency for managers on agent workloads 


## System Requirements
- Java 11 or higher
- Maven (or other build tool as per project)
- Runtime environment with file permissions (for loading and saving agent data)


## Installation and Running
1. Clone the server repository:
```
git clone https://github.com/noa662/server-side-project.git
```
2. Clone the client repository:
```
git clone https://github.com/noa662/client-side-project.git
```


### Running the Server
Follow project instructions, for example:
```
mvn clean install
java -jar target/server-side-project.jar
```

### Running the Client (Console)
After cloning, run the console interface with:
```
mvn clean install
java -jar target/client-side-project.jar
```


## Usage Examples
- Creating a new inquiry via console
- Viewing lists of inquiries with different statuses
- Assigning inquiries to available agents
- Simulating inquiry handling and archiving

  
## Documentation and Maintenance
- Version control via Git and Bitbucket
- Agile development methodology
- Proper coding standards, edge case handling, and maintainability

## Important Links
- [Server Repository](https://github.com/noa662/server-side-project)
- [Client Repository (Console)](https://github.com/noa662/client-side-project)

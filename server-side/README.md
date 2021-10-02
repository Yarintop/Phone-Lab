# Repair Management System - Server Side

This repo is for the backend part of the Repair Management System built with Spring.

## Project Structure:

    ├── src                         # Sources, all files that related the server code
    │   ├── main                    
    │   │   ├── java                # Folder that contains all the server code
    │   │   └── resources           # Folder that contains all the resources for the server
    │   └── test                    
    │       └── java                # Folder that contains all the tests for the server (ending with *Test.java)
    ├── pom.xml                     # Maven file, for building and testing the project
    ├── all_dependencoes.xml        # File that includes all the dependencies from the Moodle, the file is not in use
    ├── bitbucket-pipelines.yml     # Bitbucket's pipeline configuration file
    └── README.md                   # This file

## Work Style:

Every sprint, there will be a development branch named `dev-sprint-x`, while x is the number of the sprint.  
for every feature/category that we work on, we'll create a branch from `dev-sprint-x`
named: `dev-sprint-x-feature-name` (e.g. `dev-sprint-3-users`).

### Important notes:

* Generic work or fixes will be pushed to `dev-sprint-x`.
* Don't forget to pull updates from `dev-sprint-x` from time to time to keep the current branch updated, and to deal
  with conflicts before there are too many.
* When the work on a feature is finished, create a pull request (PR) in BitBucket, so we could merge it from there and
  see it in history, we can also ask members to review the PR to approve it.

## Building & Testing

For day to day, we test & build our server with IntelliJ IDEA & VS Code.  
We can also build & test with maven:

```bash
mvn package
```

This will build & run the tests, this will mostly be used in the bitbucket pipeline and docker later on.

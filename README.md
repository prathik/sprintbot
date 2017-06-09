# Sprint Bot

![Demo Image](demo.png?raw=true "Demo Image")

## Features

* For all open tasks ask the status
* If no task is present as to create one
* Resolve as done on reply as yes
* Ask status only for start date less than or equal to current date
* Recieve digest at a specified time
* Reply with `Yes` to resolve on Jira

## Setup

Create config.properties in `src/main/resources`.

```
username=jirausername
password=jirapassword
slackToken=slacktoken
endpoint=https://jira.corp.domain.com
```

Add your users in `src/main/resources/users.xml`.

## Running

Create the jar using `mvn package`.

Run the `jar` file in `/target/`.

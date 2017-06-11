# Sprint Bot
[![Build Status](https://travis-ci.org/prathik/sprintbot.svg?branch=master)](https://travis-ci.org/prathik/sprintbot) [![Coverage Status](https://coveralls.io/repos/github/prathik/sprintbot/badge.svg?branch=master)](https://coveralls.io/github/prathik/sprintbot?branch=master)

![Demo Image](demo.png?raw=true "Demo Image")

## Features

* For all open tasks ask the status
* If no task is present as to create one
* Resolve as done on reply as yes
* Ask status only for start date less than or equal to current date
* Recieve digest at a specified time
* Reply with `Yes` to resolve on Jira
* Very easy to add custom handlers for answers

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

## Adding new answer handlers

Answer handlers are interfaced using `AnswerActionHandler`. 

The lifecycle is that a question is first asked to the user and then it is polled for an answer by the `Question` class.

`CommonHandlersFactory` has a getCommonHandlers method which has all the handlers used by a `Question`. 
 
 You can add your handler in that method.
 
 ```java
 public static List<AnswerActionHandler> getCommonHandlers(Jira jira) {
        if(handlersMap.get(jira) == null) {
            final AnswerActionHandler answerActionHandler = new ResolveOnYes(jira);
            handlersMap.put(jira, new ArrayList<AnswerActionHandler>() {{
                add(answerActionHandler);
            }});
        }
        return handlersMap.get(jira);
    }
```

Create a new class similar to `ResolveOnYes` interfaced using ActionHandler and add it using the `add method` on to the list.



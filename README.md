# Status

An open source, self hosted status page application for communicating service incidents to customers.

It is heavily inspired by status.heroku.com and other status page applications.

A demo is available at https://systemstatus.herokuapp.com/

Please feel free to contribute if you have ideas.

![](https://raw.githubusercontent.com/owainlewis/status/master/public/images/preview.png)

![](https://raw.githubusercontent.com/owainlewis/status/master/public/images/preview2.png)

## Getting started

## Configuration

| Config Value  | Description                  |
|---------------|------------------------------|
| auth.username | The admin username for login |
| auth.password | The admin password for login |
|               |                              |

## API

Status comes with a simple JSON api for adding data to your system and for integration with other services.

Create a new incident

```
curl -iXPOST http://localhost:9000/api/v1/incidents \
-H "Content-Type: application/json" \
-d '{"title": "Database servers are down", "description": "We are experiencing database failures in US region"}'
```

Delete an incident

```
curl -iXDELETE http://localhost:9000/api/v1/incidents/5
```

## Integrations

TODO (integrations with twitter, slack etc)

When an update is posted, also push a notification to twitter and slack.




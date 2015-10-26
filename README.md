# Status (Work In Progress)

An open source, self hosted status page application for communicating service incidents to customers.

It is heavily inspired by status.heroku.com and other status page applications.

A demo is available at https://systemstatus.herokuapp.com/

Username: admin
Password: 1234

Please feel free to contribute if you have ideas.

![](https://raw.githubusercontent.com/owainlewis/status/master/public/images/previewa.png)

![](https://raw.githubusercontent.com/owainlewis/status/master/public/images/previewb.png)

## TODO

+ Service overview page
+ Web hook integrations (Slack)
+ Twitter Integration

## Getting started

The quickest way to get your own version running is to use Heroku. Assuming you have a Heroku account then the app can be deployed in under a minute.

```
heroku create 
# Generate a random secret key and set as an environment variable
heroku config:set APPLICATION_SECRET="SECRETKEY"
heroku config:set STATUS_USERNAME="username"
heroku config:set STATUS_PASSWORD="password"
git push heroku master
```

## Local development

Status is a standard Play framework application written in Scala. It requires PostgreSQL. 

If you are familar with the Scala ecosystem (and have activator installed) then you can run with:

```
createdb status && activator run 
```

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




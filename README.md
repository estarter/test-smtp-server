# test-smtp-server

Simple docker-based smtp server for testing needs with web interface and REST api.

## how to run
```bash
mvn -Dsmtp.port=10025 clean spring-boot:run
```

Run the client module to validate running instance of the server.

Interacting with the server:
```bash
sendemail -f from1@test.net -t recipient1@test.net -s localhost:10025 -u "test $(date +%Y.%m.%d-%H:%M:%S)" -m "test email"
open http://localhost:8080/
curl http://localhost:8080/emails
```

## REST API

* `/emails` - prints out all emails
* `/user/{from}/emails` - prints out all emails in `{from}` mailbox
* `/emails/1` - prints out specific email
* `/delete/1` - delete specific email
* `/delete` - delete all emails

## how to build a docker image

```bash
docker build -t test-smtp-server .
docker run -it --rm test-smtp-server
```

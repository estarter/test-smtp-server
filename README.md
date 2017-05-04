# test-smtp-server
simple docker-based smtp server for testing needs

# how to run
```bash
mvn clean package && docker build -t test_smtp_server . && docker run --rm -p 5005:5005 -p 8080:8080 -p 10025:25 -it test_smtp_server
```

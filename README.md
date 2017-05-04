# test-smtp-server
simple docker-based smtp server for testing needs

# how to run
```bash
mvn clean package && docker build -t test_smtp_server . && docker run --rm -p 8181:8181 -it test_smtp_server /opt/karaf/bin/karaf
```

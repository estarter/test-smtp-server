FROM maven:3.5.2-jdk-8

COPY . /workdir
# FIXME: skip the tests during the image build - we need to build the server for 25 port, and the test will fail with this port.
RUN cd /workdir && mvn -Dsmtp.port=25 -DskipTests package

FROM java:8-jdk
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

ENV TERM xterm-256color

RUN apt-get update && \
    apt-get install -y vim ack-grep aptitude less net-tools mc zip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
RUN echo "set completion-ignore-case on" >> /etc/inputrc 
# setup timezone
RUN echo "Europe/Zurich" > /etc/timezone && \
    dpkg-reconfigure -f noninteractive tzdata

RUN echo "#!/bin/sh\ntail --retry -f /var/log/server.log" > /usr/bin/dlog && \
    chmod +x /usr/bin/dlog

RUN echo "#!/bin/sh\njava -jar /opt/server.jar > /var/log/server.log" > /usr/bin/run.sh && \
    chmod +x /usr/bin/run.sh

COPY --from=0 /workdir/target/server-*.jar /opt/server.jar


# web interface
EXPOSE 8080
# smtp
EXPOSE 25

WORKDIR /opt

CMD ["/usr/bin/run.sh"]

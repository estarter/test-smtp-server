FROM maven:3.5.2-jdk-8

COPY . /workdir
RUN cd /workdir && mvn package

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

RUN echo -ne "#!/bin/sh\ntail --retry -f /opt/karaf/data/log/karaf.log" > /usr/bin/dlog && \
    chmod +x /usr/bin/dlog

COPY --from=0 /workdir/runtime/target/runtime-*.zip /opt/runtime.zip
COPY --from=0 /workdir/server/target/dependency/* /opt/karaf/deploy/
COPY --from=0 /workdir/server/target/server-*.jar /opt/karaf/deploy/.

RUN cd /opt && unzip runtime.zip -d karaf && rm runtime.zip && \
    mv /opt/karaf/*/* /opt/karaf && rm -rf /opt/karaf/runtime*

# web interface
EXPOSE 8080
# smtp
EXPOSE 25
# debug
EXPOSE 5005

WORKDIR /opt/karaf

CMD ["bin/karaf", "debug"]
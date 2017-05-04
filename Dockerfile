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


COPY runtime/target/runtime-*.zip /opt/runtime.zip
RUN cd /opt && unzip runtime.zip -d karaf && rm runtime.zip && \
    mv /opt/karaf/*/* /opt/karaf && rm -rf /opt/karaf/runtime*
#COPY server/target/dependency/*.jar /opt/karaf/deploy/.
COPY server/target/dependency/* /opt/karaf/deploy/
COPY server/target/server-*.jar /opt/karaf/deploy/.

# web interface
EXPOSE 8181
# smtp
EXPOSE 25

WORKDIR /opt/karaf

CMD ["bin/karaf", "debug"]
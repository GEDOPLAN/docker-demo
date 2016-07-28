FROM localhost:5000/jdk8

ENV WILDFLY_VERSION 10.0.0.Final
ENV JBOSS_HOME /opt/jboss/wildfly

RUN apk --update add curl && \
cd $HOME && \
  mkdir /opt/jboss && \
  curl  -s -S -O https://download.jboss.org/wildfly/"$WILDFLY_VERSION"/wildfly-"$WILDFLY_VERSION".tar.gz && \
  tar xf wildfly-"$WILDFLY_VERSION".tar.gz && \
  mv $HOME/wildfly-"$WILDFLY_VERSION" "$JBOSS_HOME" && \
  rm wildfly-"$WILDFLY_VERSION".tar.gz && \
  curl  -s -S -O -L https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.39.tar.gz && \
  tar xf mysql-connector-java-5.1.39.tar.gz && \
  mv mysql-connector-java-5.1.39/mysql-connector-java-5.1.39-bin.jar "$JBOSS_HOME"/standalone/deployments/mysql-connector.jar && \
  rm mysql-connector-java-5.1.39.tar.gz && \
  rm -rf mysql-connector-java-5.1.39 && \
  apk del curl

ADD standalone-full.xml $JBOSS_HOME/standalone/configuration

ENV LAUNCH_JBOSS_IN_BACKGROUND true

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0","-c","standalone-full.xml"]

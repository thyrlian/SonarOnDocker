# ====================================================================== #
#
# SonarOnDocker
# SonarQube + MySQL
#
# extracted from:
# official SonarQube  https://hub.docker.com/_/sonarqube/
# official MySQL      https://hub.docker.com/_/mysql/
#
# ====================================================================== #


# Base image
# official Java  https://hub.docker.com/_/java/
# which is based on Debian
# ---------------------------------------------------------------------- #
FROM java:openjdk-8u72-jdk


# Author
# ---------------------------------------------------------------------- #
MAINTAINER Jing Li <thyrlian@gmail.com>


# Environment variables
# ---------------------------------------------------------------------- #
ENV SONARQUBE_HOME /opt/sonarqube
ENV SONARQUBE_JDBC_USERNAME sonar
ENV SONARQUBE_JDBC_PASSWORD sonar
ENV SONARQUBE_JDBC_URL jdbc:h2:tcp://localhost:9092/sonar
ENV SONAR_VERSION 5.4


# Expose ports
# ---------------------------------------------------------------------- #
EXPOSE 9000


# Execute commands
# ---------------------------------------------------------------------- #
RUN gpg --keyserver ha.pool.sks-keyservers.net --recv-keys F1182E81C792928921DBCAB4CFCA4A29D26468DE
RUN set -x \
  && cd /opt \
  && curl -o sonarqube.zip -fSL https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-$SONAR_VERSION.zip \
  && curl -o sonarqube.zip.asc -fSL https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-$SONAR_VERSION.zip.asc \
  && gpg --batch --verify sonarqube.zip.asc sonarqube.zip \
  && unzip sonarqube.zip \
  && mv sonarqube-$SONAR_VERSION sonarqube \
  && rm sonarqube.zip* \
  && rm -rf $SONARQUBE_HOME/bin/*


# Mount volumes
# ---------------------------------------------------------------------- #
VOLUME ["$SONARQUBE_HOME/data", "$SONARQUBE_HOME/extensions"]


# Run
# ---------------------------------------------------------------------- #
WORKDIR $SONARQUBE_HOME
COPY run.sh $SONARQUBE_HOME/bin/
ENTRYPOINT ["./bin/run.sh"]

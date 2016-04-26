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
  && curl -o sonarqube.zip -fkSL https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-$SONAR_VERSION.zip \
  && curl -o sonarqube.zip.asc -fkSL https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-$SONAR_VERSION.zip.asc \
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
COPY sonarqube-entrypoint.sh $SONARQUBE_HOME/bin/
ENTRYPOINT ["./bin/sonarqube-entrypoint.sh"]


# Environment variables
# ---------------------------------------------------------------------- #
ENV MYSQL_MAJOR 5.7
ENV MYSQL_VERSION 5.7.12-1debian8


# Execute commands
# ---------------------------------------------------------------------- #
RUN apt-get update && apt-get install -y debconf-utils
RUN groupadd -r mysql && useradd -r -g mysql mysql
RUN mkdir /docker-entrypoint-initdb.d
RUN apt-get update && apt-get install apt-utils -y
RUN apt-get update && apt-get install -y perl pwgen --no-install-recommends && rm -rf /var/lib/apt/lists/*
RUN apt-key adv --keyserver ha.pool.sks-keyservers.net --recv-keys A4A9406876FCBD3C456770C88C718D3B5072E1F5
RUN echo "deb http://repo.mysql.com/apt/debian/ jessie mysql-${MYSQL_MAJOR}" > /etc/apt/sources.list.d/mysql.list

RUN { \
		echo mysql-community-server mysql-community-server/data-dir select ''; \
		echo mysql-community-server mysql-community-server/root-pass password ''; \
		echo mysql-community-server mysql-community-server/re-root-pass password ''; \
		echo mysql-community-server mysql-community-server/remove-test-db select false; \
	} | debconf-set-selections \
	&& apt-get update && apt-get install -y mysql-server="${MYSQL_VERSION}" && rm -rf /var/lib/apt/lists/* \
	&& rm -rf /var/lib/mysql && mkdir -p /var/lib/mysql
RUN sed -Ei 's/^(bind-address|log)/#&/' /etc/mysql/my.cnf \
	&& echo 'skip-host-cache\nskip-name-resolve' | awk '{ print } $1 == "[mysqld]" && c == 0 { c = 1; system("cat") }' /etc/mysql/my.cnf > /tmp/my.cnf \
	&& mv /tmp/my.cnf /etc/mysql/my.cnf


# Mount volumes
# ---------------------------------------------------------------------- #
VOLUME /var/lib/mysql


# Run
# ---------------------------------------------------------------------- #
COPY mysql-entrypoint.sh /usr/local/bin/
RUN ln -s usr/local/bin/mysql-entrypoint.sh /entrypoint.sh # backwards compat
ENTRYPOINT ["mysql-entrypoint.sh"]

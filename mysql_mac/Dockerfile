# ====================================================================== #
#
# MySQL
# official MySQL  https://hub.docker.com/_/mysql/
# Try to solve the mounting volume permission issue on Mac OSX
#
# ====================================================================== #


# Base image
# ---------------------------------------------------------------------- #
FROM mysql:latest


# Author
# ---------------------------------------------------------------------- #
MAINTAINER Jing Li <thyrlian@gmail.com>


# Execute commands
# ---------------------------------------------------------------------- #
RUN usermod -u 1000 mysql \
  && mkdir -p /var/run/mysqld \
  && chmod -R 777 /var/run/mysqld

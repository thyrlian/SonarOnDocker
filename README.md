# Sonar On Docker

Perfect Docker Compose to configure and run **SonarQube** + **MySQL** docker applications.

[![](https://img.shields.io/badge/Docker%20Hub-info-blue.svg)](https://hub.docker.com/r/thyrlian/sonar/)
[![Build Status](https://travis-ci.org/thyrlian/SonarOnDocker.svg?branch=master)](https://travis-ci.org/thyrlian/SonarOnDocker)

<img src="https://github.com/thyrlian/SonarOnDocker/blob/master/Banner.png">

```
                        ##         .
                  ## ## ##        ==
               ## ## ## ## ##    ===
           /"""""""""""""""""\___/ ===
      ~~~ {~~ ~~~~ ~~~ ~~~~ ~~~ ~ /  ===- ~~~
           \______ o           __/
             \    \         __/
              \____\_______/
```

## Getting Started

### Setup

1. Pull the latest docker images for [**SonarQube**](https://hub.docker.com/_/sonarqube/) and [**MySQL**](https://hub.docker.com/_/mysql/):

    ```console
    docker pull sonarqube
    docker pull mysql
    ```

2. (Optional - only for Mac)

    * Build a custom MySQL image for Mac:

        ```console
        docker build -t mysql_mac [PATH_OF_THIS_REPO_ON_YOUR_DISK]/mysql_mac/
        ```

    * Edit ***docker-compose.yml***, replace `image: mysql` by `image: mysql_mac`.

### Play

```console
docker-compose -f [PATH_OF_THIS_REPO_ON_YOUR_DISK]/docker-compose.yml up
```

## Persist Data

* All historical analysis data, imported rules, changed settings are saved here.

    ```
    /var/lib/mysql
    ```

* SonarQube's plugins.

    ```
    /opt/sonarqube/extensions
    ```

Don't persist ElasticSearch indices (which is located at `/opt/sonarqube/data/es`), let it rebuild by itself (otherwise could cause problem during upgrading).

## Upgrading

⚠ Always keep a **backed up database** in case upgrade fails and roll back is needed.

### MySQL

1. Perform a logical backup on the old version of MySQL

    ```console
    mysqldump -u sonar -p --opt sonar > [path_to_mysql_backup]/sonar.sql
    ```

2. Start a MySQL docker container (new version of MySQL)

    ```console
    docker run -i -t -v [path_to_mysql_backup]:/tmp -v [path_to_persist_db]:/var/lib/mysql mysql /bin/bash
    ```

3. Start MySQL server

    ```console
    /etc/init.d/mysql start
    ```

4. Start MySQL client

    ```console
    mysql
    ```

5. Create and use the database

    ```sql
    create database sonar;
    use sonar;
    ```

6. Grant privileges to user

    ```sql
    grant all on sonar.* to 'sonar'@'%' identified by 'sonar';
    grant all on sonar.* to 'sonar'@'localhost' identified by 'sonar';
    grant usage on *.* to sonar@localhost identified by 'sonar';
    grant all privileges on sonar.* to sonar@localhost;
    ```

7. Restore the backup file (by executing SQL script)

    ```sql
    source /tmp/sonar.sql
    ```

8. Quit MySQL client

    ```sql
    exit
    ```

9. Stop MySQL server

    ```console
    /etc/init.d/mysql stop
    ```

Now you have successfully restored the database on the new version of MySQL.  The database data are stored in ***path_to_persist_db*** of your host.

### SonarQube

SonarQube Server upgrade process is automated, you have nothing to manually change in the SonarQube Database.

**Migration path**: `[your_version] -> LTS (if exists) -> [expected_version]`

[Upgrading guide by SonarQube](http://docs.sonarqube.org/display/SONAR/Upgrading) (just for reference, please follow below steps.)

Don't try to stop the SonarQube server, if you kill the process, the SonarQube container exits immediately.  So you can't really upgrade SonarQube by hand within its container.  Don't worry, just try below steps.

Steps:

1. Use the new sonarqube image in ***docker-compose.yml***;
2. Run `docker-compose up`;
3. Wait until sonarqube is up.

For big SonarQube upgrading, it also requires database upgrading, but this happens automatically.

1. After the new SonarQube container is up, open its web page, you'll be redirected to a maintenance page;

    ```console
    sonarqube_1  | 2099.12.31 12:00:00 WARN  web[o.s.s.p.DatabaseServerCompatibility] Database must be upgraded. Please backup database and browse /setup
    sonarqube_1  | 2099.12.31 12:00:00 INFO  web[o.s.s.p.Platform] DB needs migration, entering safe mode
    ```

2. Open ***http://[your_sonarqube_url]:9000/setup***;

3. Click the **Upgrade** button.

The database upgrade can take several minutes.  When the DB migration ends successfully, the page will display "Database is up-to-date", then redirect you to home page.

## N.B.

There is a permission problem when mounting a host directory in MySQL container using boot2docker.

```console
[ERROR] InnoDB: Operating system error number 13 in a file operation.
[ERROR] InnoDB: The error means mysqld does not have the access rights to the directory.
```

So if you use Mac OS X, please try the approach below:

1. Build a custom MySQL image:

    ```console
    docker build -t mysql_mac mysql_mac/
    ```
2. Edit ***docker-compose.yml***, replace `image: mysql` by `image: mysql_mac`.

##License
Copyright (c) 2016 Jing Li. **SonarOnDocker** is released under the GNU General Public License version 3. See the [LICENSE](https://github.com/thyrlian/SonarOnDocker/blob/master/LICENSE) file for details.

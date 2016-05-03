# Sonar On Docker

A Docker image contains **SonarQube** + **MySQL**

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

## Persist Data

* ```/var/lib/mysql```

All historical analysis data, imported rules, changed settings are saved here.

* ```/opt/sonarqube/extensions```

SonarQube's plugins.

* ```/opt/sonarqube/data/es```

**Optional**: ElasticSearch indices, no need to be persisted, will be auto-generated.

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
2. Edit *docker-compose.yml*, replace `image: mysql` by `image: mysql_mac`.

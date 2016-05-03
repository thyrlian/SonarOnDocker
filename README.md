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

## N.B.

There is a permission problem when mounting a host directory in MySQL container using boot2docker.  So if you use Mac OS X, please try the approach below:

1. Build a custom MySQL image:

    ```console
    docker build -t mysql_mac mysql_mac/
    ```
2. Edit *docker-compose.yml*, replace `image: mysql` by `image: mysql_mac`.

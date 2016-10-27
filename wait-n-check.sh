#!/bin/bash

HOST=0.0.0.0
PORT=3306
WAIT=1

while ! nc -v -n -z -w1 $HOST $PORT
do
  echo "Waiting..."
  sleep $WAIT
done
echo "Service is up!"

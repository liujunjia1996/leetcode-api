#/bin/bash
jps | grep api | awk  '{print $1}' | xargs kill -9
nohup java -jar api-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
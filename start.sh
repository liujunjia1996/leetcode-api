#/bin/bash
jps | grep api | awk  '{print $1}' | xargs kill -9
nohup java -jar api.jar > /dev/null 2>&1 &
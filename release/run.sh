#!/bin/sh
# nohup java -jar workerlib.jar -Dwatchdocker=true >aftask.log 2>&1 &
nohup java -Xms255m -Xmx255m -Xss255k -Duser.timezone=GMT+08 -jar workerlib.jar >ccweb.log 2>&1 &
echo $! > tpid
echo Start workerlib.jar Success!
exit

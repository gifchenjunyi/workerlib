#!/bin/sh
# nohup java -jar workerlib.jar -Dwatchdocker=true >aftask.log 2>&1 &
nohup java -Xms1024m -Xmx2048m -Xss512k -Duser.timezone=GMT+08 -jar workerlib.jar >ccweb.log 2>&1 &
echo $! > tpid
echo Start workerlib.jar Success!
exit

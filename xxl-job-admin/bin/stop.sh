#!/usr/bin/env bash

#jarName
projectName=${project.build.finalName}
pid=`ps -ef |grep 'java' |grep $projectName |awk '{print $2}'`
if [ -n "$pid" ]; then
    kill -9 $pid
    echo "kill server pid:"$pid
    sleep 3
fi


#!/usr/bin/env bash

#jarName
projectName=${project.build.finalName}
target=dev
port=${server.port}

if [ -n "$1" ]; then
   target=$1
fi
echo "target = "$target

pid=`ps -ef |grep 'java' |grep $projectName |awk '{print $2}'`
if [ -n "$pid" ]; then
    kill -9 $pid
    sleep 3
fi
DATE=`date +%Y-%m-%d`
LogFileName=${HOME}/logs/$projectName

if [ ! -d $LogFileName ]; then
    mkdir -p $LogFileName
fi

case $target in
    local)
        JAVA_OPTS="-Xms2048m -Xmx2048m -Xss256k -Xmn256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -XX:SurvivorRatio=8"
        JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$LogFileName/gc.log"
        JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LogFileName/dump"
        JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=$target -Dspring.config.location=../conf/ -Dapollo_meta=$apollo_meta"
        nohup java -jar $JAVA_OPTS ../boot/$projectName*.jar >> $LogFileName/start.$DATE.log 2>&1 &
    ;;
    prod)
        JAVA_OPTS="-Xms256m -Xms256m -Xss256k -Xmn128m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -XX:SurvivorRatio=8"
        JAVA_OPTS="$JAVA_OPTS -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$LogFileName/gc.log"
        JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LogFileName/dump"
        JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=$target -Dspring.config.location=../conf/ -Dapollo_meta=$apollo_meta"
        nohup java -jar $JAVA_OPTS ../boot/$projectName*.jar >> $LogFileName-start.$DATE.log 2>&1 &
    ;;
    *)
        echo "No this ENV:$target."
        exit 1
    ;;
esac

  echo "Deploy Success"
  exit 0


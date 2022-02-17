#!/bin/bash

if [ -d update ];then
  rm -rf update
fi

mkdir update
for jar in `ls */target/*.jar`;do
  cp $jar update/
done

rm -rf update/*sources.jar

if [ -f update.tar.gz ];then
  rm -rf update.tar.gz
fi

cd update && tar -cvzf update.tar.gz ./* && mv update.tar.gz ../ && cd ../

rm -rf update

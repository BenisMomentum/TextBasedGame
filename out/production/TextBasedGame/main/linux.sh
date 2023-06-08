#!/bin/sh

BASEDIR=$(dirname $0)

cd $BASEDIR/TextBasedGame/TextBasedGame || exit

java -cp out/production/TextBasedGame main.Main

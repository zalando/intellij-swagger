#!/usr/bin/env bash

if [ -z "$1" ]
  then
    echo "Missing version for master branch"
    exit 1
fi

if [ -z "$2" ]
  then
    echo "Missing version for 162.x branch"
    exit 1
fi

git checkout master
./gradlew updateVersion -Pversion=$1
./gradlew buildPlugin -Pversion=$1
git add src/main/resources/META-INF/plugin.xml
git commit -m "Bump version"

git checkout 162.x
./gradlew updateVersion -Pversion=$2
./gradlew buildPlugin -Pversion=$2
git add src/main/resources/META-INF/plugin.xml
git commit -m "Bump version"
git checkout master
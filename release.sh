#!/usr/bin/env bash
set -eux

if [ -z "$1" ]
  then
    echo "Missing version for master branch"
    exit 1
fi

export $(grep -v '^\#' .env)
if [ -z "$JETBRAINS_HUB_TOKEN" ]
  then
    echo "Please put JETBRAINS_HUB_TOKEN in .env: JETBRAINS_HUB_TOKEN=<token>"
    exit 1
fi

NEW_VERSION=$1

sed -i "s/<version>.*<\/version>/<version>$NEW_VERSION<\/version>/g" src/main/resources/META-INF/plugin.xml

git checkout master
./gradlew publishPlugin -Pversion=$NEW_VERSION -x buildSearchableOptions
git add src/main/resources/META-INF/plugin.xml
git commit -m "Bump version"


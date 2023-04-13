#!/usr/bin/env bash
set -euo pipefail

if [ -z "${1:-}" ]
  then
    echo "Version needs to be provided."
    echo "Usage: ./release.sh <version> <release-channel>"
    exit 1
fi

if [[ "${2:-}" != "stable" && "${2:-}" != "beta" ]]
  then
    echo "Release channel needs to be provided - supported values: stable, beta"
    echo "Usage: ./release.sh <version> <release-channel>"
    exit 1
fi


ENVS=$(grep -v '^\#' .env || true)
if [[ -n "${ENVS}" ]]
  then
    export "${ENVS?}"
fi

if [ -z "${JETBRAINS_HUB_TOKEN:-}" ]
  then
    echo "Please put JETBRAINS_HUB_TOKEN in .env: JETBRAINS_HUB_TOKEN=<token>"
    exit 1
fi

NEW_VERSION=$1

sed -i '' "s/<version>.*<\/version>/<version>$NEW_VERSION<\/version>/g" src/main/resources/META-INF/plugin.xml

git checkout master
./gradlew publishPlugin -Pversion="${NEW_VERSION}" -PjetbrainsReleaseChannel="${2}" -x buildSearchableOptions
git add src/main/resources/META-INF/plugin.xml
git commit -m "Bump version"


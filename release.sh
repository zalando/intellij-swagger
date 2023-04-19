#!/usr/bin/env bash
set -euo pipefail

if [[ "$#" -lt 3 || -z "${1}" || -z "${2}" || -z "${3}" ]]
  then
    echo "Version, release channel and release notes need to be provided."
    echo "Usage: ./release.sh <version> <release-channel> <release-notes>"
    exit 1
fi

if [[ "${2}" != "stable" && "${2}" != "beta" ]]
  then
    echo "Unsupported release channel ${2} - supported values: stable, beta."
    echo "Usage: ./release.sh <version> <release-channel>"
    exit 1
fi


ENVS=$(grep -v '^\#' .env || true)
if [[ -n "${ENVS}" ]]
  then
    export "${ENVS?}"
fi

if [[ -z "${JETBRAINS_HUB_TOKEN:-}" ]]
  then
    echo "Please put JETBRAINS_HUB_TOKEN in .env: JETBRAINS_HUB_TOKEN=<token>"
    exit 1
fi

if [[ "$(git branch --show-current)" != "master" ]]
  then
    echo "Please use master branch"
    exit 1
fi

./gradlew publishPlugin -Pversion="${1}" -PjetbrainsReleaseChannel="${2}" -PchangeNotes="${3}" -x buildSearchableOptions

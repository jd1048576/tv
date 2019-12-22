#!/bin/bash

OUTPUT="${GITHUB_WORKSPACE}/apk"

function main() {
  export PATH=${PATH}:$(go env GOPATH)/bin
  go get github.com/tcnksm/ghr
  ghr -t ${GITHUB_TOKEN} \
      -u ${GITHUB_ACTOR} \
      -r ${GITHUB_REPOSITORY#*/} \
      -c ${GITHUB_SHA} \
      -n "Latest" \
      -b "Latest Master Build" \
      -delete \
      LATEST ${OUTPUT}
}

main
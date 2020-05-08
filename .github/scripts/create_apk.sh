#!/bin/bash

BUNDLE_VERSION="0.14.0"
BUNDLE_TOOL="https://github.com/google/bundletool/releases/download/${BUNDLE_VERSION}/bundletool-all-${BUNDLE_VERSION}.jar"
OUTPUT="${GITHUB_WORKSPACE}/apk"

function create_apk() {
  variant=${1}
  store_file="app/.signing/app-${2}.jks"
  store_password=${3}
  key_alias=${4}
  key_password=${5}

  java -jar bundletool-all-${BUNDLE_VERSION}.jar build-apks \
    --bundle=app/build/outputs/bundle/${variant}/app-${variant}.aab \
    --output=app/build/outputs/apk/${variant}/app-${variant}.apks \
    --mode=universal \
    --ks=${store_file} \
    --ks-pass=pass:${store_password} \
    --ks-key-alias=${key_alias} \
    --key-pass=pass:${key_password}

  unzip -q app/build/outputs/apk/${variant}/app-${variant}.apks -d app/build/outputs/apk/${variant}/
  mv app/build/outputs/apk/${variant}/universal.apk ${OUTPUT}/tv-${variant}.apk
}

function main() {
  mkdir -p ${OUTPUT}
  echo "Downloading Bundle tool ${BUNDLE_VERSION}"
  wget -q ${BUNDLE_TOOL}
  echo "Creating Debug Apk"
  create_apk debug debug android debug android
  echo "Creating Release Apk"
  create_apk release debug android debug android
}

main

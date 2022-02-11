#!/bin/bash -x

# The following parameters must be specified:
#   build_number - specifies the number of JetBrainsRuntime build
#   bundle_type  - specifies bundle to be built;possible values:
#               <empty> or nomod - the release bundles without any additional modules (jcef)
#               jcef - the release bundles with jcef
#               fd - the fastdebug bundles which also include the jcef module
#
# This script packs test-image along with JDK images when bundle_type is set to "jcef".
# If the character 't' is added at the end of bundle_type then it also makes test-image along with JDK images.
#

source jb/project/tools/common/scripts/common.sh

JBSDK_VERSION=$1
JDK_BUILD_NUMBER=$2
build_number=$3
bundle_type=$4

function pack_jbr {
  __bundle_name=$1
  __arch_name=$2

  [ "$bundle_type" == "fd" ] && [ "$__arch_name" == "$JBRSDK_BUNDLE" ] && __bundle_name=$__arch_name && fastdebug_infix="fastdebug-"
  JBR=${__bundle_name}-${JBSDK_VERSION}-windows-x64-${fastdebug_infix}b${build_number}

  echo Creating $JBR.tar.gz ...

  /usr/bin/tar -czf $JBR.tar.gz -C $BASE_DIR $__arch_name || do_exit $?
}

[ "$bundle_type" == "nomod" ] && bundle_type=""

JBRSDK_BUNDLE=jbrsdk
RELEASE_NAME=windows-x86_64-server-release
IMAGES_DIR=build/$RELEASE_NAME/images
BASE_DIR=.

if [ "$bundle_type" == "jcef" ] || [ "$bundle_type" == "dcevm" ] || [ "$bundle_type" == "fd" ]; then
  jbr_name_postfix="_${bundle_type}"
fi

pack_jbr jbr${jbr_name_postfix} jbr
pack_jbr jbrsdk${jbr_name_postfix} jbrsdk

if [ "$bundle_type" == "jcef" ]; then
  JBRSDK_TEST=$JBRSDK_BUNDLE-$JBSDK_VERSION-windows-test-x64-b$build_number
  echo Creating $JBRSDK_TEST.tar.gz ...
  /usr/bin/tar -czf $JBRSDK_TEST.tar.gz -C $IMAGES_DIR --exclude='test/jdk/demos' test || do_exit $?
fi
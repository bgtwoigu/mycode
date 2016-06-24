#!/bin/bash

export JAVA_HOME=/opt/java-7-openjdk-amd64
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

GERRIT_CHECK_JOB_NAME=$1
CHECK_CODE_ENV=$2
WHICH_SIDE=$3
PRJ_CODE=$4

rm_updated_code(){
  echo "Delete updated code"
  rm -rf ${UPDATE_CODE_TARGET}
}

CURRENT_JENKINS_DIR=`ls -t ~/.jenkins/jobs/${GERRIT_CHECK_JOB_NAME}/builds/ | head -1`

index=`cat ~/.jenkins/jobs/${GERRIT_CHECK_JOB_NAME}/builds/${CURRENT_JENKINS_DIR}/log | grep "Checking out" | awk -F " " '{print $5}' | awk -F "(" '{print $2}' | tr -d ")" | awk -F "/" '{print $1}'`

reponame=`cat ~/.jenkins/jobs/${GERRIT_CHECK_JOB_NAME}/builds/${CURRENT_JENKINS_DIR}/log | grep "remote.$index.url" | awk -F " " '{print $5}' | awk -F "29418/" '{print $2}'`

echo index:$index
echo reponame:$reponame

while read n
do
  name=`echo $n | awk -F "name=\"" '{print $2}' | awk -F "\"" '{print $1}' | tr -d '\n' | tr -d ' '`
  if [ "$name" == "$reponame" ] ; then
    echo n:$n
    REPO_PATH=`echo $n | awk -F "path=\"" '{print $2}' | awk -F "\"" '{print $1}' | tr -d '\n' | tr -d ' '`
    echo REPO_PATH:$REPO_PATH
    break
  fi
done < ~/${CHECK_CODE_ENV}/../.repo/manifest.xml

UPDATE_CODE_TARGET="/work/home/jenkins/${CHECK_CODE_ENV}/../${REPO_PATH}"

echo UPDATE_CODE_TARGET:$UPDATE_CODE_TARGET

echo "cp -rf /work/home/jenkins/.jenkins/workspace/${GERRIT_CHECK_JOB_NAME}/* ${UPDATE_CODE_TARGET}/"
cp -rf /work/home/jenkins/.jenkins/workspace/${GERRIT_CHECK_JOB_NAME}/* ${UPDATE_CODE_TARGET}/

cd ~/${CHECK_CODE_ENV}/${WHICH_SIDE}

if [ -f "mk_all_msm.sh" ] ; then
  ./mk_all_msm.sh MSM_${PRJ_CODE}
  error=$?
  echo "error=${error} PIPESTATUS=${PIPESTATUS[0]}"
  rm_updated_code
  repo sync
  if [ ${PIPESTATUS[0]} -ne 0 -o ${error} -ne 0 -o -f compile_msm_error.log ];then
    exit 1
  else
    exit 0
  fi
else
  cpu_core=`cat /proc/cpuinfo | grep "processor" | wc -l`
  source build/envsetup.sh
  lunch 1
  make -j${cpu_core}
  rm_updated_code
  repo sync
  OK_BUILD=`ls out/target/product/*/system.img`
  if [ ${OK_BUILD} == "" ] ; then
    exit 1
  else
    exit 0
  fi
fi

#!/bin/bash

# 
# Copyright (C) 2014 The oneplus pre-build script
# author: zoufeng
# date:2014-12-05
# ================================================

export JAVA_HOME=/opt/java-7-openjdk-amd64
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
export USE_CCACHE=1

echo "jenkins input args $*"

while getopts t:f:b:e:o:c:p:r: OPTION
do
  case $OPTION in
  t) ONEPLUS_VERSION_TYPE=$OPTARG
     ;;
  f) SUFF_VTYPE=$OPTARG
     ;;
  b) BASEVERSION=$OPTARG
  	 ;;     
  e) ENFORCE=$OPTARG
     ;;
  o) ONEPLUS_BUILD_TYPE=$OPTARG
     ;;
  c) PRJ_CODE=$OPTARG
  	 ;;
  p) PACK_ENV_FLAG=$OPTARG
     ;;
  r) REGION=$OPTARG
     ;;
  esac
done

if [ "$ONEPLUS_BUILD_TYPE" = "DEFAULT" ] ; then
  ONEPLUS_BUILD_TYPE=""
fi

PATHROOT=$(dirname $(pwd))
AP_PATH=${PATHROOT}/AP
MODEM_PATH=${PATHROOT}/MODEM
PARAMETER_FILE=${PATHROOT}/.script/${PRJ_CODE}/PARAMENTER_FILE_${BASEVERSION}.txt
WEEKDAY=$(date +%w)
SINCEDATE=$(date  --date="2 days ago" --rfc-3339='date')
DATE_TIME=$(date +%Y-%m-%d-%H-%M) 
MONTH_TIME=$(date +%Y-%m)
RDATE_TIME=$(date +%Y-%m-%d)
HOUR_TIME=$(date +%H)
HDATE_IMTE=$(date +%Y-%m-%d-%H)
SDATE_TIME=$(date +%y%m%d_%H%M)
YDATE_TIME=$(date +%y%m%d)
AP="false"
MODEM="false"
AP_FLAG="true"
MODEM_FLAG="true"
UPDATE_FLAG=0
cpu_core=`cat /proc/cpuinfo | grep "processor" | wc -l`
#MODEM_DIR="msm8974"
OTA_VERSION="000"

SSH_URL=`cat ${PARAMETER_FILE} | grep "^SSH_URL" | awk -F " " '{print $2}'`
AP_MANIFEST_FILE=`cat ${PARAMETER_FILE} | grep "^AP_MANIFEST_FILE" | awk -F " " '{print $2}'`
MODEM_MANIFEST_FILE=`cat ${PARAMETER_FILE} | grep "^MODEM_MANIFEST_FILE" | awk -F " " '{print $2}'`
CHIP_NAME=`cat ${PARAMETER_FILE} | grep "^CHIP_NAME" | awk -F " " '{print $2}'`
RELEASE=`cat ${PARAMETER_FILE} | grep "^RELEASE_PATH" | awk -F " " '{print $2}'`
MSM_WORKBASE_NAME=`cat ${PARAMETER_FILE} | grep "^MSM_WORKBASE_NAME" | awk -F " " '{print $2}'`
PRJ_MODEL=`cat ${PARAMETER_FILE} | grep "^PRJ_MODEL" | awk -F " " '{print $2}'`
HW_VERSION=`cat ${PARAMETER_FILE} | grep "^HW_VERSION" | awk -F " " '{print $2}'`
SW_VERSION=`cat ${PARAMETER_FILE} | grep "^SW_VERSION" | awk -F " " '{print $2}'`
OTA_VERSION=`cat ${PARAMETER_FILE} | grep "^OTA_VERSION" | awk -F " " '{print $2}'`
OUT_CUSTOMIZE=`cat ${PARAMETER_FILE} | grep "^OUT_CUSTOMIZE" | awk -F " " '{print $2}'`
MODEM_DIR=`cat ${PARAMETER_FILE} | grep "^MODEM_DIR" | awk -F " " '{print $2}'`
USERDATA64=`cat ${PARAMETER_FILE} | grep "^USERDATA64" | awk -F " " '{print $2}'`
SYSTEMIMAGE=`cat ${PARAMETER_FILE} | grep "^SYSTEMIMAGE" | awk -F " " '{print $2}'`
PARTITION_SIZE=`cat ${PARAMETER_FILE} | grep "^PARTITION_SIZE" | awk -F " " '{print $2}'`
ELF=`cat ${PARAMETER_FILE} | grep "^ELF_LIST" | awk -F ":" '{print $2}'`
if [[ ${PACK_ENV_FLAG} = "false" ]];then
    CURRENT_OUT_DIRECTORY=MSM_${PRJ_CODE}_${SDATE_TIME}_${SUFF_VTYPE}
else
    CURRENT_OUT_DIRECTORY=MSM_${PRJ_CODE}_${SDATE_TIME}_${SUFF_VTYPE}_include_out
fi
VERSION_OUT=${PATHROOT}/version_out/${CURRENT_OUT_DIRECTORY}
if [[ ${ONEPLUS_BUILD_TYPE} = "cta" ]];then
    IMG_NAME=${PRJ_MODEL}_${HW_VERSION}_cta.${SW_VERSION}
elif [[ ${ONEPLUS_BUILD_TYPE} = "aging" ]];then
    IMG_NAME=${PRJ_MODEL}_${HW_VERSION}_aging.${SW_VERSION}
else
    IMG_NAME=${PRJ_MODEL}_${HW_VERSION}_A.${SW_VERSION}
fi
IMG_FULL_NAME=${IMG_NAME}_${YDATE_TIME}
OTA_NAME=${PRJ_MODEL}_${HW_VERSION}_OTA_${OTA_VERSION}
VERSION_OUT_IMAGES=${VERSION_OUT}/${IMG_FULL_NAME}
LAST_VERSION_OUT_IMAGES=${PATHROOT}/version_out/last_version
VERSION_OUT_TMP=${PATHROOT}/version_out/tmp

ELF_LIST=`echo $ELF | sed "s/ /#/g"`


if [ "$OUT_CUSTOMIZE" != "true" ]; then
out_dir=out/target/product/${CHIP_NAME}
else
out_dir=out_MSM_${PRJ_CODE}/target/product/${CHIP_NAME}
fi

if [ ! -d "${AP_PATH}/temp_file" ] ; then
    mkdir -p ${AP_PATH}/temp_file
fi

if [ ! -d "${MODEM_PATH}/temp_file" ] ; then
    mkdir -p ${MODEM_PATH}/temp_file
fi

if [ ! -d "${VERSION_OUT}/Debug" ] ; then
    mkdir -p ${VERSION_OUT}/Debug
fi

if [ ! -d "${VERSION_OUT_TMP}" ] ; then
    mkdir -p ${VERSION_OUT_TMP}
fi

if [ ! -d "${LAST_VERSION_OUT_IMAGES}" ] ; then
    mkdir -p ${LAST_VERSION_OUT_IMAGES}
fi


echo VARIANTS LIST
echo =======================================
echo PATHROOT:$PATHROOT
echo WEEKDAY:$WEEKDAY
echo SINCEDATE:$SINCEDATE
echo DATE_TIME:$DATE_TIME
echo MONTH_TIME:$MONTH_TIME
echo RDATE_TIME:$RDATE_TIME
echo HOUR_TIME:$HOUR_TIME
echo HDATE_IMTE:$HDATE_IMTE
echo CPU_CORE:$cpu_core
echo MSM_DIR:$MSM_WORKBASE_NAME
echo MODEM_DIR:$MODEM_DIR
echo IMG_NAME:$IMG_FULL_NAME
echo RELEASE:$RELEASE
echo ONEPLUS_VERSION_TYPE:$ONEPLUS_VERSION_TYPE
echo SUFF_VTYPE:$SUFF_VTYPE
echo REGION:$REGION
echo BASEVERSION:$BASEVERSION
echo ENFORCE:$ENFORCE
echo ONEPLUS_BUILD_TYPE:$ONEPLUS_BUILD_TYPE
echo PRJ_CODE:$PRJ_CODE
echo PACK_ENV_FLAG:$PACK_ENV_FLAG
echo =======================================

get_ap_code()
{
        echo =======================================
        echo .repo directory will be initialized by repo
        echo SSH URL is ${SSH_URL} and the manifest file is ${AP_MANIFEST_FILE}
        echo initialing...
        echo repo init ${SSH_URL} -m ${AP_MANIFEST_FILE}
        repo init -u ${SSH_URL} -m ${AP_MANIFEST_FILE}
        echo the initialize is done, now execute the repo sync command
        repo sync
        if [ $? -ne 0 ];then
            echo "get_codefailed!"
            exit 1
        else
            echo repo sync is done
        fi
        echo ======================================= 
}

get_modem_code()
{
        echo =======================================
        echo .repo directory will be initialized by repo
        echo SSH URL is ${SSH_URL} and the manifest file is ${MODEM_MANIFEST_FILE}
        echo initialing...
        echo repo init -u  ${SSH_URL} -m ${MODEM_MANIFEST_FILE}
        repo init -u ${SSH_URL} -m ${MODEM_MANIFEST_FILE}
        echo the initialize is done, now execute the repo sync command
        repo sync
        if [ $? -ne 0 ];then
            echo "get_codefailed!"
            exit 1
        else
            echo repo sync is done
        fi
        echo =======================================
}

check_changes(){
    CHANGE_RECORD=$1
    WHICH_PART=$2
    LAST_MANIFEST_FILE_NAME=`ls ${LAST_VERSION_OUT_IMAGES}/Debug/snapshot*${WHICH_PART}*.xml`
    if [ -f "${LAST_MANIFEST_FILE_NAME}" -a -d "${LAST_VERSION_OUT_IMAGES}" ]; then
        diff snapshot-${PRJ_CODE}-$DATE_TIME.xml ${LAST_MANIFEST_FILE_NAME} > ${CHANGE_RECORD}/diff.xml
        if [ "`cat ${CHANGE_RECORD}/diff.xml`" != "" ]; then
            rm ${CHANGE_RECORD}/*.txt
            while read line
            do
                name=`echo $line | awk -F "path=\"" '{print $2}' | awk -F "\"" '{print $1}' | tr -d '\n' | tr -d ' '`
                if [ "$name" != "" ]; then
                    if [ -f ${CHANGE_RECORD}/modify_list.txt ]; then
                        flag=`cat ${CHANGE_RECORD}/modify_list.txt | grep $name`
                            if [ "$flag" != "" ]; then
                                continue
                            fi
                    fi
                    revision_temp=`cat ${CHANGE_RECORD}/diff.xml | grep \"${name}\" | awk -F "revision=\"" '{print $2}' | awk -F "\"" '{print $1}'`
                    revision_container=`echo $revision_temp | sed 's/ /#/'`
                    revision_1=`echo ${revision_container} | awk -F "#" '{print $2}'`
                    revision_2=`echo $revision_container | awk -F "#" '{print $1}'`
                    echo REPO NAME:$name >> ${CHANGE_RECORD}/modify_list.txt
                    cd $name
                    git log ${revision_1}..${revision_2} --stat >> ${CHANGE_RECORD}/modify_list.txt
                    cd - 1>/dev/null 2>&1
                fi
            done < ${CHANGE_RECORD}/diff.xml
            rm ${CHANGE_RECORD}/diff.xml
            UPDATE_FLAG=1
        else
            UPDATE_FLAG=0
        fi
    else
        UPDATE_FLAG=1
    fi
    LAST_VERSION_ERROR_FLAG=`echo $LAST_USER_VERSION | grep -i 'ERROR'`
    if [ x$LAST_VERSION_ERROR_FLAG != x"" ]; then
		UPDATE_FLAG=1
    fi
}

fn_copy_android()
{
    if [ "$AP" = "true" ] || [ "$ENFORCE" = "AP" ] ; then
        cp -rvf ${out_dir}/emmc_appsboot.mbn               ${VERSION_OUT_IMAGES}
        cp -rvf ${out_dir}/boot.img                        ${VERSION_OUT_IMAGES}
        cp -rvf ${out_dir}/bootdebug.img                   ${VERSION_OUT}/Debug
        cp -rvf ${out_dir}/cache.img                       ${VERSION_OUT_IMAGES}
        cp -rvf ${out_dir}/recovery.img                    ${VERSION_OUT_IMAGES}
        cp -rvf ${out_dir}/system.img                      ${VERSION_OUT_IMAGES}
        cp -rvf ${out_dir}/persist.img                     ${VERSION_OUT_IMAGES}

        if [ "{$PRJ_CODE}" = "14001" ];then
            cp ${out_dir}/userdata.img                     ${VERSION_OUT_IMAGES}
            cp ${out_dir}/userdata_64G.img                 ${VERSION_OUT_IMAGES}
#           rm -rf ${out_dir}/userdata_64G.img
        else
            cp ${out_dir}/userdata.img                     ${VERSION_OUT_IMAGES}
            cp ${out_dir}/userdata_64G.img                 ${VERSION_OUT_IMAGES}
        fi
    else
       
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/emmc_appsboot.mbn          ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/boot.img                   ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/bootdebug.img              ${VERSION_OUT}/Debug
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/cache.img                  ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/recovery.img               ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/system.img                 ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/persist.img                ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/userdata.img               ${VERSION_OUT_IMAGES}
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/${IMG_NAME}*/userdata_64G.img           ${VERSION_OUT_IMAGES}
    fi

}

fn_copy_emmc_img()
{       
        #if OUT_CUSTOMIZE is false, use "cp -vf" to distinguish China and OverSeas logo.bin
        if [ "$OUT_CUSTOMIZE" != "true" ];then
            cp -vf emmc_img/*                                  ${VERSION_OUT_IMAGES}
        else
            cp -rvf emmc_img/*                                 ${VERSION_OUT_IMAGES}
        fi
#        cp -rvf emmc_img/*.txt                             ${VERSION_OUT}/Configuration_And_Log
#       rm bin/Android.mk
}

del_old_env()
{
	echo "del old evn only keep three days"
	cd ${RELEASE}/
	ENVNAME=`find . -mtime +3 -type f -name "out_MSM_${PRJ_CODE}*.env.tar.bz2"`
    for arg in ${ENVNAME} ; do
        echo "rm -rf $arg"
        rm -rf $arg
    done
    cd -
}

del_old_img()
{
	echo "del img evn only keep last three"
	cd ${RELEASE}/
	
	IMGNAME=`find . -type d -name "${IMG_NAME}*"`
	BASEIMGNAME=(`basename ${IMGNAME} | sort`)
	COUNT=${#BASEIMGNAME[@]}
	for ((i=0; i<${COUNT}-8; i++))
        do	
        local RMPATH=`find . -type d -name  ${BASEIMGNAME[i]}`
        rm -rf ${RMPATH}
        done
	cd -
}

md5_check()
{
	array_var=(8974_msimage.mbn dynamic_nvbk.bin emmc_appsboot.mbn \
	gpt_backup0.bin gpt_backup0_64G.bin gpt_main0.bin gpt_main0_64G.bin \
	userdata.img userdata_64G.img rawprogram0.xml rawprogram0_64G.xml \
	NON-HLOS.bin MPRG8974.mbn rpm.mbn sbl1.mbn sdi.mbn static_nvbk.bin tz.mbn \
	boot.img cache.img persist.img recovery.img system.img)

#	addtional_var=(boot cache persist recovery system)
	echo "md5 check ..."
	cd ${VERSION_OUT_IMAGES}
	
#	for arg in ${addtional_var[*]};do
#	    num=`ls ${arg}*.img | wc -l`
#	    if [[ ${num} -gt 1 ]];then
#	        rm -rf ${arg}.img
#	    fi
#	    var=`ls ${arg}*.img`
#	    array_var=(${array_var[*]} ${var})
#	done

	if [[ -f md5sum.md5 ]];then
	    rm -rf md5sum.md5
	fi

	for ((i=0; i<${#array_var[@]}; i++))
	do
	    if [[ -f ${array_var[$i]} ]];then
	        md5sum ${array_var[$i]} >> md5sum.md5
	    fi
	done
	cd -
	echo "finish md5 check"
}

#echo "jenkins" | sudo -S echo "hello root"
echo ===============================================
echo clean AP and MODEM environment!
echo Cleaning...
if [ -f "${VERSION_OUT_TMP}/CLEAN_FLAG" ] ; then
    AP_FLAG=`cat ${VERSION_OUT_TMP}/CLEAN_FLAG | grep "^AP" | awk -F ":" '{print $2}'`
    MODEM_FLAG=`cat ${VERSION_OUT_TMP}/CLEAN_FLAG | grep "^MODEM" | awk -F ":" '{print $2}'`
fi
if [ "$AP_FLAG" = "true" ] ; then
    echo deleting ${AP_PATH}/${MSM_WORKBASE_NAME}...
    rm -rf ${AP_PATH}/${MSM_WORKBASE_NAME}/*
fi
if [ "$MODEM_FLAG" = "true" ] ; then
    echo deleting ${MODEM_PATH}/${MSM_WORKBASE_NAME}...
    rm -rf ${MODEM_PATH}/${MSM_WORKBASE_NAME}/*
fi
echo Clean Done!!
echo ===============================================
#get source files from git
echo "${PATHROOT} sync source from git"

echo "SSH_URL:	${SSH_URL}" > ${VERSION_OUT}/Debug/compile.info
echo "AP_MANIFEST_FILE:	${AP_MANIFEST_FILE}" >> ${VERSION_OUT}/Debug/compile.info
echo "MODEM_MANIFEST_FILE:${MODEM_MANIFEST_FILE}" >> ${VERSION_OUT}/Debug/compile.info

cd ${AP_PATH}
get_ap_code
if [ $? -ne 0 ];then
        echo "get_codefailed!"
        exit 1
fi
repo manifest -r -o snapshot-${PRJ_CODE}-$DATE_TIME.xml
check_changes ${AP_PATH}/temp_file ap
if [ "$UPDATE_FLAG" = "1" ] ; then
    AP="true"
    mv ${AP_PATH}/temp_file/modify_list.txt ${VERSION_OUT}/Debug/ap_modify_list.txt
    mv snapshot-${PRJ_CODE}-$DATE_TIME.xml ${VERSION_OUT}/Debug/snapshot-${PRJ_CODE}-ap-$DATE_TIME.xml
else
    echo There is no any change in AP side!!
    LAST_MANIFEST_FILE_NAME=`ls ${LAST_VERSION_OUT_IMAGES}/Debug/snapshot-${PRJ_CODE}-ap-*.xml`
    cp -rvf ${LAST_MANIFEST_FILE_NAME} ${VERSION_OUT}/Debug/snapshot-${PRJ_CODE}-ap-$DATE_TIME-NOUPDATE.xml
    touch ${VERSION_OUT}/Debug/ap_no_modify.txt
fi

cd ${MODEM_PATH}
get_modem_code
if [ $? -ne 0 ];then
        echo "get_codefailed!"
        exit 1
fi
repo manifest -r -o snapshot-${PRJ_CODE}-$DATE_TIME.xml
check_changes ${MODEM_PATH}/temp_file modem
if [ "$UPDATE_FLAG" = "1" ] ; then
    MODEM="true"
    mv ${MODEM_PATH}/temp_file/modify_list.txt ${VERSION_OUT}/Debug/modem_modify_list.txt
    mv snapshot-${PRJ_CODE}-$DATE_TIME.xml ${VERSION_OUT}/Debug/snapshot-${PRJ_CODE}-modem-$DATE_TIME.xml
else
    echo There is no any change in MODEM side!!
    LAST_MANIFEST_FILE_NAME=`ls ${LAST_VERSION_OUT_IMAGES}/Debug/snapshot-${PRJ_CODE}-modem-*.xml`
    cp -rvf ${LAST_MANIFEST_FILE_NAME} ${VERSION_OUT}/Debug/snapshot-${PRJ_CODE}-modem-$DATE_TIME-NOUPDATE.xml
    touch ${VERSION_OUT}/Debug/modem_no_modify.txt
fi

if [ "$AP" = "false" ] && [ "$MODEM" = "false" ]; then
    echo There is no any change in both AP and MODEM side!!
    if [ "$ENFORCE" = "MODEM" ] || [ "$ENFORCE" = "AP" ] || [ "$ENFORCE" = "BOTH" ]; then
      echo Compile ${ENFORCE} any way!
    else
      echo Compiliation is Over!
      echo Deleting version at compile server...
      rm -rf ${VERSION_OUT}
      exit 0
    fi
fi

echo "begin compile MODEM MODEM=$MODEM  ENFORCE=$ENFORCE"
if [ "$MODEM" = "true" ] || [ "$ENFORCE" = "MODEM" ] || [ "$ENFORCE" = "BOTH" ]; then
    cd ${MODEM_PATH}/${MSM_WORKBASE_NAME}/${MODEM_DIR}
    if [ "$OUT_CUSTOMIZE" != "true" ];then
        ./mk_all_msm.sh ${ONEPLUS_BUILD_TYPE} | tee build-${PRJ_CODE}-modem-$DATE_TIME.log
    else
        ./mk_all_msm.sh MSM_${PRJ_CODE} | tee build-${PRJ_CODE}-modem-$DATE_TIME.log
    fi
    error=$?
    echo "error=${error} PIPESTATUS=${PIPESTATUS[0]}"
    if [ ${PIPESTATUS[0]} -ne 0 -o ${error} -ne 0 -o -f compile_msm_error.log ];then
        echo "MODEM build failed! Please check the log"
        echo "$USER" | mkdir -p ${VERSION_OUT}/Debug
        echo "$USER" | cp build-${PRJ_CODE}-modem-$DATE_TIME.log ${VERSION_OUT}/Debug/build-${PRJ_CODE}-modem-$DATE_TIME-FAILED.log
        COMMIT_AUTHOR_TMP=`cat ${VERSION_OUT}/Debug/modem_modify_list.txt | grep Author: | awk -F "<" '{print $2}' | tr -d ">" | sort | uniq | grep oneplus`
        COMMIT_AUTHOR=`echo ${COMMIT_AUTHOR_TMP} | sed "s/ /\//g"`
        echo "COMMIT_AUTHOR=`echo ${COMMIT_AUTHOR_TMP} | sed "s/ /\//g"`"
        /work/home/jenkins/script/send_email.sh ${COMMIT_AUTHOR} ${BASEVERSION} ${PRJ_CODE}
        mv ${VERSION_OUT} ${VERSION_OUT}_ERROR
        VERSION_OUT=${VERSION_OUT}_ERROR
        mv ${VERSION_OUT} ${RELEASE}
        exit 1
    fi
    MODEM="true"
    mv build-${PRJ_CODE}-modem-$DATE_TIME.log ${VERSION_OUT}/Debug
fi

echo ===============================
echo Coping modem files for OTA...
    cd ${PATHROOT}/.script/${PRJ_CODE}
    if [ "$OUT_CUSTOMIZE" != "true" ];then
    ./jenkins_modem_ota.sh -m ${AP_PATH}/${MSM_WORKBASE_NAME}/android/vendor/oneplus/prebuilt/modem -p ${MODEM_PATH}/${MSM_WORKBASE_NAME}/${MODEM_DIR} -t ${MODEM} -r ${LAST_VERSION_OUT_IMAGES} -l ${AP_PATH}/${MSM_WORKBASE_NAME}/android
    else
    ./jenkins_modem_ota.sh -m ${AP_PATH}/${MSM_WORKBASE_NAME}/android/vendor/oppo/prebuilt/modem -p ${MODEM_PATH}/${MSM_WORKBASE_NAME}/${MODEM_DIR} -t ${MODEM} -r ${LAST_VERSION_OUT_IMAGES} -l ${AP_PATH}/${MSM_WORKBASE_NAME}/android
    fi
    cd -
echo Copy is done
echo ===============================

echo "begin compile AP AP=$AP  ENFORCE=$ENFORCE"
if [ "$AP" = "true" ] || [ "$ENFORCE" = "AP" ] || [ "$ENFORCE" = "BOTH" ]; then
    cd ${AP_PATH}/${MSM_WORKBASE_NAME}
    if [ "$OUT_CUSTOMIZE" != "true" ];then
        cp -rvf ${AP_PATH}/../.script/compile.sh  ${AP_PATH}/${MSM_WORKBASE_NAME}
        echo "./compile.sh ${PRJ_MODEL} ${SUFF_VTYPE} ${REGION} ${ONEPLUS_BUILD_TYPE}"
        ./compile.sh ${PRJ_MODEL} ${SUFF_VTYPE} ${REGION} ${ONEPLUS_BUILD_TYPE} 2>&1 | tee build-${PRJ_CODE}-ap-$DATE_TIME.log
    else
        cp -rvf ${AP_PATH}/../.script/compile_customize.sh  ${AP_PATH}/${MSM_WORKBASE_NAME}
        echo "./compile_customize.sh MSM_${PRJ_CODE} ${ONEPLUS_VERSION_TYPE} ${ONEPLUS_BUILD_TYPE}"
        ./compile_customize.sh MSM_${PRJ_CODE} ${ONEPLUS_VERSION_TYPE} ${ONEPLUS_BUILD_TYPE} 2>&1 | tee build-${PRJ_CODE}-ap-$DATE_TIME.log
    fi
    OK_BUILD=`cat build-${PRJ_CODE}-ap-$DATE_TIME.log | grep "system.img"`
    if [ "${OK_BUILD}" != "" ] ; then
        echo BUILD SUCCESS!
    else
        echo BUILD FAILED!!!
        mv ${VERSION_OUT} ${VERSION_OUT}_ERROR
        VERSION_OUT=${VERSION_OUT}_ERROR
        cp build-${PRJ_CODE}-ap-$DATE_TIME.log ${VERSION_OUT}/Debug/build-${PRJ_CODE}-ap-$DATE_TIME-FAILED.log
        COMMIT_AUTHOR_TMP=`cat ${VERSION_OUT}/Debug/ap_modify_list.txt | grep Author: | awk -F "<" '{print $2}' | tr -d ">" | sort | uniq | grep oneplus`
        COMMIT_AUTHOR=`echo ${COMMIT_AUTHOR_TMP} | sed "s/ /\//g"`
        echo "COMMIT_AUTHOR=`echo ${COMMIT_AUTHOR_TMP} | sed "s/ /\//g"`"
        /work/home/jenkins/script/send_email.sh ${COMMIT_AUTHOR} ${BASEVERSION} ${PRJ_CODE}
        mv ${VERSION_OUT} ${RELEASE}
        exit 1
    fi
    AP="true"
    mv build-${PRJ_CODE}-ap-$DATE_TIME.log ${VERSION_OUT}/Debug
fi

echo "begin packaging AP=$AP   MODEM=$MODEM    ENFORCE=$ENFORCE"
if [ "$AP" = "true" ] || [ "$MODEM" = "true" ] || [ "$ENFORCE" != "NO-NEED" ]; then
    if [ ! -d ${VERSION_OUT_IMAGES} ]; then
        mkdir -p ${VERSION_OUT_IMAGES}
    fi

    cd ${AP_PATH}/${MSM_WORKBASE_NAME}/android

    if [ "$OUT_CUSTOMIZE" != "true" ]; then
        out/host/linux-x86/bin/make_ext4fs -s -T ${SYSTEMIMAGE} -S out/target/product/${CHIP_NAME}/root/file_contexts -l ${PARTITION_SIZE} -a system out/target/product/${CHIP_NAME}/system.img out/target/product/${CHIP_NAME}/obj/PACKAGING/target_files_intermediates/*/SYSTEM/
        if [ "$USERDATA64" != "" ] ; then
            out/host/linux-x86/bin/make_ext4fs -s -l ${USERDATA64} -a data out/target/product/${CHIP_NAME}/userdata_64G.img out/target/product/${CHIP_NAME}/data
        fi # if [ "$USERDATA64" != "" ]
    else
        if [ "$USERDATA64" != "" ] ; then
            out_MSM_${PRJ_CODE}/host/linux-x86/bin/make_ext4fs -s -l ${USERDATA64} -a data out_MSM_${PRJ_CODE}/target/product/${CHIP_NAME}/userdata_64G.img out_MSM_${PRJ_CODE}/target/product/${CHIP_NAME}/data
        fi # if [ "$USERDATA64" != "" ]
    fi #if [ "$OUT_CUSTOMIZE" != "true" ]

    fn_copy_android
    cd ${PATHROOT}/.script/${PRJ_CODE}
    if [ "$OUT_CUSTOMIZE" != "true" ]; then
        ./jenkins_modem_copy.sh -v ${VERSION_OUT_IMAGES} -p ${MODEM_PATH}/${MSM_WORKBASE_NAME}/${MODEM_DIR} -t ${MODEM} -i ${IMG_NAME} -r ${RELEASE} -l ${LAST_VERSION_OUT_IMAGES} -o ${AP_PATH}/${MSM_WORKBASE_NAME}/android/${out_dir}
    else
        ./jenkins_modem_copy.sh -v ${VERSION_OUT_IMAGES} -p ${MODEM_PATH}/${MSM_WORKBASE_NAME}/${MODEM_DIR} -t ${MODEM} -i ${IMG_NAME} -r ${RELEASE} -e ${ELF_LIST} -l ${LAST_VERSION_OUT_IMAGES} -o ${AP_PATH}/${MSM_WORKBASE_NAME}/android/${out_dir}
    fi
    cd ${AP_PATH}/${MSM_WORKBASE_NAME}/android
    fn_copy_emmc_img
    md5_check
    if [[ -z ${ONEPLUS_BUILD_TYPE} ]];then
        touch ${VERSION_OUT_IMAGES}/rev_${SDATE_TIME}
    else
        touch ${VERSION_OUT_IMAGES}/rev_${SDATE_TIME}_${ONEPLUS_BUILD_TYPE}
    fi
    cd ${VERSION_OUT}     
    tar -I lbzip2 -cf ${IMG_FULL_NAME}.tar.bz2 ${IMG_FULL_NAME}
    md5sum ${IMG_FULL_NAME}.tar.bz2 > ${IMG_FULL_NAME}.tar.md5
     
    cd ${AP_PATH}/${MSM_WORKBASE_NAME}/android
    if [ "$AP" = "true" ] || [ "$ENFORCE" = "AP" ] || [ "$ENFORCE" = "BOTH" ]; then
        if [ "$OUT_CUSTOMIZE" != "true" ]; then
        INTER_DIR=out/target/common/obj
        else
        INTER_DIR=out_MSM_${PRJ_CODE}/target/common/obj
        fi
        inter_zipname=${VERSION_OUT}/Debug/jar_intermediates
        ALL_DIR=`find $INTER_DIR -name "*_intermediates"`
        DEBUG_JAR=classes-full-debug.jar
        mkdir -p $inter_zipname
        for dd in $ALL_DIR
        do
            bname=${dd%_intermediates}
            jar_name=`basename $bname`.jar
            if [ -f "$dd/$DEBUG_JAR" ]; then
                cp -f $dd/$DEBUG_JAR $inter_zipname/${jar_name}
            fi
        done
        if [ "$OUT_CUSTOMIZE" != "true" ]; then
            RES_APK_PATH=out/target/product/${CHIP_NAME}/obj/APPS/framework-res_intermediates
        else
            RES_APK_PATH=out_MSM_${PRJ_CODE}/target/product/${CHIP_NAME}/obj/APPS/framework-res_intermediates
        fi
        cp -rvf ${RES_APK_PATH}/package.apk ${inter_zipname}/framework-res.apk

        if [ "$OUT_CUSTOMIZE" != "true" ]; then
            SYMBOL_PATH=out/target/product/$CHIP_NAME
        else
            SYMBOL_PATH=out_MSM_${PRJ_CODE}/target/product/$CHIP_NAME
        fi

        tar -czf ${VERSION_OUT}/Debug/symbols.tar.gz ${SYMBOL_PATH}/symbols

        cd ${out_dir}
        OTA_ZIP=`find . -name *-target_files-*.zip`
        cp -rvf ${OTA_ZIP} ${VERSION_OUT}/Debug
        cp -rvf obj/KERNEL_OBJ/System.map ${VERSION_OUT}/Debug
        cp -rvf obj/KERNEL_OBJ/.config ${VERSION_OUT}/Debug/kernel_config
        cp -rvf ramdisk.img ${VERSION_OUT}/Debug
        cp -rvf ramdisk-recovery.img ${VERSION_OUT}/Debug
        cp -rvf bootdebug.img ${VERSION_OUT}/Debug

        OTANAME=`find . -maxdepth 1 -type f -name "${CHIP_NAME}-ota-*.zip"`
        mkdir ${VERSION_OUT}/OTA
        mkdir ${VERSION_OUT}/OTA_WIPE
	echo "OTANAME = ${OTANAME}"
	#for ota in ${OTANAME}
	#do
            #cp -rvf ${ota} ${VERSION_OUT}/OTA
	#done
        if [ -f *_mmc.zip ]; then
	        cp -rf *_mmc.zip                      ${VERSION_OUT}/OTA/${OTA_NAME}_all.zip
	        cp -rf *_mmc_wipedata.zip             ${VERSION_OUT}/OTA_WIPE/${OTA_NAME}_all_wipe.zip
	        cd ${VERSION_OUT}/OTA/
	        md5sum ${OTA_NAME}_all.zip > ${OTA_NAME}_all.zip.md5
	        cd -
	        cd ${VERSION_OUT}/OTA_WIPE/
	        md5sum ${OTA_NAME}_all_wipe.zip > ${OTA_NAME}_all_wipe.zip.md5
	        cd -
        fi

        if [ -f *_mmc_incremental.zip ]; then
	        cp -rf *_mmc_incremental.zip          ${VERSION_OUT}/OTA/${OTA_NAME}_patch.zip
	        cp -rf *_mmc_incremental_wipedata.zip ${VERSION_OUT}/OTA_WIPE/${OTA_NAME}_patch_wipe.zip
	        cd ${VERSION_OUT}/OTA/
	        md5sum ${OTA_NAME}_patch.zip > ${OTA_NAME}_patch.zip.md5
	        cd -
	        cd ${VERSION_OUT}/OTA_WIPE/
	        md5sum ${OTA_NAME}_patch_wipe.zip > ${OTA_NAME}_patch_wipe.zip.md5
	        cd -
        fi	   
    else
        mkdir ${VERSION_OUT}/Debug/
        mkdir ${VERSION_OUT}/OTA
        mkdir ${VERSION_OUT}/OTA/OTA_WIPE
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/jar_intermediates ${VERSION_OUT}/Debug/
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/symbols.tar.gz ${VERSION_OUT}/Debug/
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/*bz2 ${VERSION_OUT}/Debug/
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Configuration_And_Log/*.zip ${VERSION_OUT}/Debug/
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/System.map ${VERSION_OUT}/Debug
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/kernel_config ${VERSION_OUT}/Debug
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/ramdisk.img ${VERSION_OUT}/Debug
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/ramdisk-recovery.img ${VERSION_OUT}/Debug
        cp -rvf ${LAST_VERSION_OUT_IMAGES}/Debug/bootdebug.img ${VERSION_OUT}/Debug
        cd ${AP_PATH}/${MSM_WORKBASE_NAME}/android/${out_dir}
        OTANAME=`find . -maxdepth 1 -type f -name "${OTA_NAME}*.zip"`
        echo "OTANAME = ${OTANAME}"
        if [ -f *_mmc.zip ]; then
            cp -rf *_mmc.zip                      ${VERSION_OUT}/OTA/${OTA_NAME}_all.zip
	        cp -rf *_mmc_wipedata.zip             ${VERSION_OUT}/OTA_WIPE/${OTA_NAME}_all_wipe.zip
	        cd ${VERSION_OUT}/OTA/
	        md5sum ${OTA_NAME}_all.zip > ${OTA_NAME}_all.zip.md5
	        cd -
	        cd ${VERSION_OUT}/OTA_WIPE/
	        md5sum ${OTA_NAME}_all_wipe.zip > ${OTA_NAME}_all_wipe.zip.md5
	        cd -	        
        fi

        if [ -f *_mmc_incremental.zip ]; then
	        cp -rf *_mmc_incremental.zip          ${VERSION_OUT}/OTA/${OTA_NAME}_patch.zip
	        cp -rf *_mmc_incremental_wipedata.zip ${VERSION_OUT}/OTA_WIPE/${OTA_NAME}_patch_wipe.zip
	        cd ${VERSION_OUT}/OTA/
	        md5sum ${OTA_NAME}_patch.zip > ${OTA_NAME}_patch.zip.md5
	        cd -
	        cd ${VERSION_OUT}/OTA_WIPE/
	        md5sum ${OTA_NAME}_patch_wipe.zip > ${OTA_NAME}_patch_wipe.zip.md5
	        cd -
        fi

    fi
#    echo "${USER}" | sudo -S mv ${VERSION_OUT} ${RELEASE}
    cp -rvf ${VERSION_OUT} ${RELEASE}
    rm -rf ${LAST_VERSION_OUT_IMAGES}/*
    mv ${VERSION_OUT}/* ${LAST_VERSION_OUT_IMAGES}
    rm -rf ${VERSION_OUT}
    echo "packing out or not AP=$AP   PACK_ENV_FLAG=${PACK_ENV_FLAG}"
    if [ "$AP" = "true" -a ${PACK_ENV_FLAG} = "true" ]; then
        cd ${AP_PATH}/${MSM_WORKBASE_NAME}/android
        tar -I lbzip2 -cf out_MSM_${PRJ_CODE}_${SDATE_TIME}_${SUFF_VTYPE}.env.tar.bz2 out
        mv out_MSM_${PRJ_CODE}_${SDATE_TIME}_${SUFF_VTYPE}.env.tar.bz2 ${RELEASE}/${CURRENT_OUT_DIRECTORY}
        del_old_env
    fi
#    echo Deleting version at compile server...
#    rm -rf ${VERSION_OUT}
#    echo "${USER}" | sudo -S chmod -R 775 ${RELEASE}/${CURRENT_OUT_DIRECTORY}
fi

echo AP:$AP > ${VERSION_OUT_TMP}/CLEAN_FLAG
echo MODEM:$MODEM >> ${VERSION_OUT_TMP}/CLEAN_FLAG

echo COMPILE FINISH!!


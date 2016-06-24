#!/bin/sh
##################################
##################################
TOP_DIR=`pwd`
##################################
##################################
usage(){
cat <<EOF

Usage: (mk) project actions [modules]

	usage: compile.sh ${TARGET_PRODUCT} [usedebug/user] [cta/cts/cmcc/cmcctest/cu/cutest/st/sttest/release]

	example:
		. compile.sh OnePlus2 userdebug
        . compile.sh OnePlus2 user
        . compile.sh OnePlus2 user release
		...
end

EOF
}

echo "begin to compile!!"
echo "##################################"

cpu_core=`cat /proc/cpuinfo | grep "processor" | wc -l`

cd ${TOP_DIR}/android
export OEM_BUILD_TYPE=""
export OEM_PRJ_CODE=""
source build/envsetup.sh
if [[ $# -ge 3 ]];then
    lunch $1-$2 $3 $4
    export OEM_BUILD_TYPE=$5
    export OEM_PRJ_CODE=$4
    make -j${cpu_core}
    if [ $? != 0 ];then
        exit 1
    fi
else
    echo "arg is empty"
	usage
fi

#make boot_aging.img
#only OP3 define 20 compile option, this don't influence other projects
source ./mk 20

#make bootdebug.img
if [ $2 = "user" ];then
cd ${TOP_DIR}/android
  source ./mk 19
fi

cd ${TOP_DIR}

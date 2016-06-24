#!/bin/bash
#dhj@OnlineRd.PM, 2013/09/23, add this file /* VENDOR_EDIT */

TOPDIR=`pwd`

source /opt/setenv5.sh

if [ "$1" = "" ]; then
    echo
    echo " 1. build modem_proc"
    echo " 2. build boot_image"
    echo " 3. build rpm_proc"
    echo " 4. build trustzone_images"
    echo " 5. build debug_images"
    echo " 6. build adsp images"
    echo " 7. build wcnss_proc"
    echo " 9. build meta"
    echo " 10. build meta for 64G eMMC"
    echo
    echo -e "Select what you want:\c"
    read params
    echo
else
    params=$1
fi

if [ ${params} = "1" ]; then
    echo "build modem_proc..."
    cd $TOPDIR/modem_proc/build/ms
    if [[ $3 = "debug" ]];then
        ./build.sh -c  8974.gen.prod BUILD_ID=AAAAANAZ BUILD_VER=3000603 OPPO_DEBUG=TRUE -k
    else
        ./build.sh 8974.gen.prod BUILD_ID=AAAAANAZ BUILD_VER=3000603 -k
    fi
    if [[ $? != 0 ]];then
        echo "build.sh 1 modem_proc make error"
#        exit 1
    fi
    cd $TOPDIR
fi

if [ ${params} = "2" ]; then
    echo "build boot_image..."     
    cd $TOPDIR/boot_images/build/ms
    ./build.sh -c  TARGET_FAMILY=8974 BUILD_ID=AAAAANAZ BUILD_VER=1234
    ./build.sh TARGET_FAMILY=8974 BUILD_ID=AAAAANAZ BUILD_VER=1234
    if [[ $? != 0 ]];then
        echo "build.sh 2 boot_image make error"
        exit 1
    fi
fi

if [ ${params} = "3" ]; then
    echo "build rpm_proc..."
    unset BUILD_ID
    unset BUILD_VER
    cd rpm_proc/build
    ./build_8974.sh -c
    ./build_8974.sh
    if [[ $? != 0 ]];then
        echo "build.sh 3 rpm_proc make error"
#        exit 1
    fi
    cd $TOP_DIR
fi

if [ ${params} = "4" ]; then
    echo "build trustzone_images..."
    cd trustzone_images/build/ms
    ./build.sh CHIPSET=msm8974 tz sampleapp tzbsp_no_xpu playready widevine isdbtmm securitytest keymaster commonlib mobicore sse
    if [[ $? != 0 ]];then
        echo "build.sh 4 tz make error"
#        exit 1
    fi
    cd $TOPDIR
fi

if [ ${params} = "5" ]; then
    echo "build debug_images..."
    cd debug_image/build/ms
    ./build.sh TARGET_FAMILY=8974 sdi BUILD_ID=AAAAANAZ BUILD_VER=8 BUILD_MIN=1
    if [[ $? != 0 ]];then
        echo "build.sh 5 debug_image make error"
        exit 1
    fi
    cd $TOPDIR
fi

if [ ${params} = "6" ]; then
    echo "build adsp images..."
    cd $TOPDIR
    cd adsp_proc/build
    python build.py
    
    if [[ $? != 0 ]];then
        echo "build.sh 6 adsp_proc make error"
        exit 1
    fi
    cd $TOPDIR
fi

#if [ ${params} = "7" ]; then
#echo "build wcnss_proc..."
#cd $TOPDIR
#cd wcnss_proc/Pronto/bsp/build
#./wcnss_build.sh 8974 pronto BUILD_ID=SCAQBAZ
#fi

if [ ${params} = "9" ]; then
    echo "build meta..."
    cd $TOPDIR/common/build
    python update_common_info.py --nonhlos
    if [[ $? != 0 ]];then
        echo "build.sh 9 update_common_info.py make error"
        exit 1
    fi
    cd $TOPDIR
fi
if [ ${params} = "10" ]; then
    echo "build meta for 64G eMMC..."
    cd $TOPDIR/common/build
    python update_common_info_for_64G_eMMC.py --nonhlos
    if [[ $? != 0 ]];then
        echo "build.sh 10 update_common_info_for_64G_eMMC.py make error"
        exit 1
    fi
    cd $TOPDIR
fi

cd $TOPDIR

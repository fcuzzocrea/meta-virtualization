SUMMARY = "A reference container host image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit core-image

DEPENDS += " podman-native"

IMAGE_INSTALL = " \
      packagegroup-core-boot \
"

CONTAINERS_TO_INSTALL ?= "app-container-curl"
do_image[mcdepends] = "mc::container:${CONTAINERS_TO_INSTALL}:do_image_complete"

ROOTFS_POSTPROCESS_COMMAND += "rootfs_install_container ; "
rootfs_install_container () {
    set +e
    # export PSEUDO_UNLOAD=1
    container_to_load=$(ls ${TOPDIR}/tmp/deploy/images/${MACHINE}/${CONTAINERS_TO_INSTALL}-*-oci.tar | head -1)
    echo "[INFO]: podman load --root=${IMAGE_ROOTFS}/var/lib/containers < $container_to_load"    
    podman load --root=${IMAGE_ROOTFS}/var/lib/containers < $container_to_load &>/dev/null
    exit 0
}

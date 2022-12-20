include ${@bb.utils.contains('DISTRO_FEATURES', 'virtualization', 'libseccomp.inc', '', d)}

#!/bin/sh
./keytool-importkeypair -k space365.jks -p 123456 -pk8 platform.pk8 -cert platform.x509.pem -alias space365


# space365.jks : 签名文件
# 123456 : 签名文件密码
# platform.pk8、platform.x509.pem : 系统签名文件
# space365: 签名文件别名

# keytool-importkeypair -k ~/.android/debug.keystore -p android -pk8 platform.pk8 -cert platform.x509.pem -alias platform

# 通过逆向手段还原微信获取签名工具的实现
通过微信工具获取到的签名信息与通过keytool获取到的签名MD5信息是一致的，区别在于通过keytool获取到的签名中间通过`:`连接，字符是大写。
使用命令keytool获取签名：
keytool -list -v -keystore test_release.jks



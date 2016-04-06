# core_java
Coding Practice when studying 《Core Java》

##  Echo

###  Client

- 读取用户输入字符串，发送给Server
- 接收Server响应，显示到标准输出

###  Server

- 新建线程为每一个Client服务
- 在字符串前加上"Echo："后返回给Client

###  增值服务（待实现）

- Client检查Server异常退出
- Server检测Client异常退出

###  实现说明

- Client使用Maven构建
- Server使用Eclipse IDE构建

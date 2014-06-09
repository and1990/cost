cost
====

1.下载安装Intellij Idea
下载地址：http://www.jetbrains.com/idea/download/ 版本：13.1.3（windows）

2.安装maven
下载地址：http://maven.apache.org/download.cgi  版本：apache-maven-3.2.1-bin.zip
安装maven：http://jingyan.baidu.com/article/295430f136e8e00c7e0050b9.html

3.安装memcache
下载地址：http://pan.baidu.com/s/1eQBZ8sU
安装教程：
	1）下载memcache的windows稳定版，解压放某个盘下面，比如在c:\memcached
	2）在终端（也即cmd命令界面）下输入 'c:\memcached\memcached.exe -d install' 安装
	3） 再输入： 'c:\memcached\memcached.exe -d start' 启动。
	NOTE: 以后memcached将作为windows的一个服务每次开机时自动启动。这样服务器端已经安装完毕了。
	
4.引入项目
	1）打开Intellij Idea
	2）引入项目文件，选择到最顶级的pom.xml文件，点击“确定”
	3）配置maven
	4）部署项目
	5）访问路径：http://localhost:8080/cost/view.do
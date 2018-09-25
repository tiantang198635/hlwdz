一、系统结构
	sign
	├── sign-common          -- 公共模块
	├── base-notice          -- 通知管理服务 |6001端口|
	├── base-customer        -- 客户管理服务 |6021端口|
	├── base-account         -- 账户管理服务 |6041端口|
	├── base-customer        -- 支付管理服务 |6061端口|
	├── base-payment         -- 产品管理服务 |6081端口|
	├── base-charging        -- 计费管理服务 |6101端口|
	├── base-cfcasign        -- CFCA电签服务 |6121端口|
	├── base-authentication  -- 认证管理服务 |6141端口|
	├── base-file					   -- 文件管理服务 |6161端口|
	├── sign-eurka           -- 注册中心服务 |8081端口|
	├── sign-gateway-zuul    -- 路由网卡服务 |9000端口|
	├── sign-config          -- 配置中心服务 |9021端口|
	├── sign-user            -- 互签用户服务 |7001端口|
	├── sign-account         -- 互签账户服务 |7021端口|
	├── sign-http            -- 互签对外服务 |7041端口|
	├── sign-contract        -- 互签合同服务 |7061端口|
	├── sign-template        -- 互签模板服务 |7081端口|
	├── sign-batch           -- 互签定时服务 

二、系统详细介绍
1.父工程
	项目名sign
		SpringCloud版本：Finchley.RELEASE
	    Springboot版本:2.0.3.RELEASE
		Jdk：Jdk1.8
		Redis:4.0.11
		Maven:3.3.3
		数据库：oracle
  
2.公共子模块
	项目名sign-common
	类目录 目录 com.sign.common
	以jar形式打包 
	说明：所有的微服务使用到的公共类尽量都在该子模块中添加
	
3.各微服务子模块包组成
	├──	configure  	配置项
	├── controller 	rest服务
  	├── dao 	   	数据库访问层
  	├── feign		调用其他微服务
	├── service		模块本身服务接口
   		├── impl	模块本身服务实现

4.各子模块的RequestMapping
 	访问各子模块rest服务时都有子模块名称开头，如微服务名base-notice，所有的url以/notice开头访问
 

5.资源文件
---resources	所有的配置文件存放目录
   ├── mapper	mybatis数据库脚本的存放目录


6.版本控制
	版本管理工具：git
	规范：
	├──	功能模块必须本地验证后才能提交
	├──	提交之前先pull（更新），避免出现其他问题
	├── 在发布第一个版本之前，我们都用master分支，在发布第一个版本之后将创建devlop分支，所有开发都在develop分支进行
	├── 每次commit详细说明提交的内容

7.其他规范
	├── 1.包名都用小写；
	├── 2.类名首字母大写  
	├── 3.类名、属性名、字段名、方法名尽量使用英文  
	├── 4.代码中多加注释
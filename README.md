# gas-manage-system
## 简介

> 基于Java Swing + MySQL 的煤气管理系统二改煤气管理系统。

### 技术栈

- Java Swing作为UI
- MySQL作为存储
- JDBC原生方法操作mysql（封装了公共的增删改查方法，参看`src.model.access.Connect.java`）
- MVC作为整体架构
- 密码加密使用MD5

### 参看

需求分析参看：[煤气管理系统需求分析](./doc/煤气管理系统需求分析.md)

ER图参看：[煤气管理系统E-R图](./doc/煤气管理系统E-R图.jpeg)

### 操作

普通用户账号：20240701001  密码：123456

管理员默认账号：3122160125  密码：456456

### 部分示意图

![登录界面](https://gitee.com/koala010/typora/raw/master/img/20210818091144.png)



![管理员端](https://gitee.com/koala010/typora/raw/master/img/20210818091207.png)



## 注意事项

编码默认使用的GBK，非UTF8

## 文件夹结构

```
- doc 文档
	-- 煤气管理系统需求分析
	-- 煤气管理系统E-R图.jpeg
- lib 需引入的jar包
- sql sql文件
- src 源代码
	-- Controller 控制层
	-- Images 图片资源
	-- Model 模型层
		--- access 数据库的增删改查操作（相当于Mybatis的mapper层）
			---- Connect.java 放置了公共的增删改查方法
		--- table 放置表相关的映射
	- Tool 工具层
	- View 视图层
```

入口文件为`src.view.Main.java`


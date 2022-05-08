# 抢红包业务模拟demo
> 可用于简单入门Redis和练习Jmeter压力测试

## 测试方法
### 准备工作
1. 下载到本地后用IDEA打开等待maven仓库加载相关依赖
2. 打开resource文件夹下的application.properties修改TODO下的信息（改成自己的）
3. 执行sql文件夹下的文件，使本地数据库有初始结构和数据
### 启动
4. 启动数据库服务
5. 启动Redis-server服务，可以用Another Redis Desktop Manager之类的工具对过程中的key进行监控
6. 运行启动类
### 接口测试
7. postman中测试发红包接口
![image](https://user-images.githubusercontent.com/65518148/167288859-28598291-2784-45dc-9c7f-58e989931f7b.png)
![image](https://user-images.githubusercontent.com/65518148/167288577-eb979625-7c77-4a7b-9b7d-e06c3afa2c4b.png)
数据库也已经生成了随即金额红包列表
![image](https://user-images.githubusercontent.com/65518148/167288692-045cee90-e78a-4343-90b9-55addfcba6d2.png)

8. 复制data数据，进行抢红包接口测试
![image](https://user-images.githubusercontent.com/65518148/167288460-c31e7629-71a4-4bc1-a017-739bb4d2fe54.png)
抢到的金额即为列表的第一个
![image](https://user-images.githubusercontent.com/65518148/167288708-af6e40f7-dcf4-4d1c-b76a-9cedbd1c0449.png)
数据库也做了相应更新
### 压力测试
9. 打开apache的Jmeter测试工具，按照图中内容做好配置工作，如果以下三步懒得配置，可以试试项目路径下的.jmx文件（不一定能用）
![image](https://user-images.githubusercontent.com/65518148/167289157-78df5a5b-3e82-4c58-b7e9-66ec5ff12961.png)
![image](https://user-images.githubusercontent.com/65518148/167288932-7dd15d0b-a2cc-426d-a84b-c085da1e6099.png)
![image](https://user-images.githubusercontent.com/65518148/167288988-a24b582c-0425-433f-a61e-0d5e502dab9c.png)
（这里的csv文件也在项目路径下）

10. 点击运行查看结果树
![image](https://user-images.githubusercontent.com/65518148/167289003-66d616d7-c378-4f4e-8f52-4bff490b7cb4.png)
![image](https://user-images.githubusercontent.com/65518148/167289061-fade63f4-28c5-41f0-a442-6e75ba000e2f.png)

11. 查看数据库结果
![image](https://user-images.githubusercontent.com/65518148/167289127-3736c00a-0ff4-466e-a0b3-716626082e35.png)

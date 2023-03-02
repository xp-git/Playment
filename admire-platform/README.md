# 赞赏平台（admire-platform、ap）

服务

```tex
端口：9020-9030
网关服务
- 9020
用户服务
- 9021
- 登录、注册、注销、修改、查看
赞赏服务（商品、订单）
- 9022
- 商品列表、商品详情、订单列表、订单详情、商品支付
```

项目启动配置

1、修改 ap-base-config 模块中的 application-dev.yml 配置文件，都改为自己的环境配置

2、修改 ap-base-config 模块中 ServerNameConstants 类的 HOST 属性值，改为自己的回调host

3、修改 ap-admire 模块中的 application.yml 配置文件中 ali.pay. 配置项的值，把应用id、私钥、公钥改为自己的即可

4、启动项目

5、分支说明：

- master 分支是三哥编写的分支
- study 分支是我从 master 分支切换过去的一份完整代码，你们需要改动最好在这个分支做动作，master 分支不要动做代码参考即可因为我不会动study分支内容，但会动master分支，如果两个人都操作master则有可能出现冲突
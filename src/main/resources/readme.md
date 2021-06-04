### 功能介绍

- 基于ContextInitializerClass完成PropertySource的注入，进而完成对
  @Value值来源的修改
- thrift rpc 实现，待进行netty集成并完成客户端连接池化，服务的注册发现(使用简单的redis list+发布订阅 实现)
- 基于redis pub/sub 动态修改配置值
- cat集成，需要部署对应的server端
- retrofit集成 返回类型Call<T>,@Path使用注意，不可以被urlencoding
- 基于micrometer的埋点
- groovy脚本的集成
- js脚本语言的使用
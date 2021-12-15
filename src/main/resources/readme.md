### 功能介绍

- 基于ContextInitializerClass完成PropertySource的注入，进而完成对
  @Value值来源的修改
- thrift rpc 实现，服务端netty集成并完成客户端连接池化，服务的注册发现(未实现！可以使用简单的redis list+发布订阅来实现)
- 基于redis pub/sub 动态修改配置值
- cat集成，需要部署对应的server端
- retrofit集成 返回类型Call<T>,@Path使用注意，不可以被urlencoding
- 基于micrometer的埋点
- groovy脚本的集成
- js脚本语言的使用
- 自定义注解，SOURCE阶段的使用，CLASS阶段完成类的添加，修改类貌似需要javaassist来处理，可曾闻java语法树-----！！！
- 基于guava的本地缓存的使用
- 通过动态家在jar包，可以在表面上隔离代码入侵，参考代码如下：
  URL url = new URL(serviceVO.getJarUrl());
  List<URL> urls = Arrays.asList(LIB_THRIFT_COMMON_URL, UTIL_RPC_BASE_URL, FAST_JSON_URL, SLF4J_URL, THRIFT_URL, RPC_CONTEXT_URL, url);
  if(StringUtils.isNotEmpty(serviceVO.getExtraJarUrl()) && serviceVO.getExtraJarUrl().endsWith(".jar")) {
  urls.add(new URL(serviceVO.getExtraJarUrl()));
  }
  URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[urls.size()]), null);
  然后使用这个loader加载各种需要的class
  
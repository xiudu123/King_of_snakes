#### 一个史诗级巨著

思维导图
![](image/mind_map.png)

>2023/3/6 0:27
>实现对局列表和排行榜界面
>实现功能：将数据库里面的对局信息和个人排名展现在前端界面上
>+ 待解决问题：
>   * 个人模式中存储的数据过大，需要压缩
>   * 实现对局回放的功能
>   * 列表的分页功能
>   * 首页


>2023/3/5/ 0:54
> 保存对局记录
> 实现功能： 将个人模式和双人模式的每局的对局记录保存于数据库中
> + 注意事项：
>   * `@Component` 注解的地方需要有无参构造函数
> + 遇到的问题：
>   * `@Component`注解到了只有有参构造函数的类导致报错
>> <font style="font-style: italic">
>> Annoyance:<br>
>> 今天打了ucup，人麻了
>> </font>

>2023/3/3
>*微服务：匹配系统*
>实现的功能：将双人匹配池单独拧出来作为一个新的springboot运行<br>
>+ 注意事项：
>   * 在Controller层放行接口时，注意 注解 `@Controller` 和 `@RestController` 的区别。
>   * `Servive`层外用到`@Autowired`注解是用在类前面加上`@Component`注解
>+ 遇到的问题：
>   * 因为在`Controller`层放行接口时，使用了 `@Controller` 作为了注解，导致后端通信失败
>   * 建立微服务时，总是出错
>+ 知识点
>   * 在微服务的父级目录的 `pom` 添加 `<packaging>pom</packaging>` 和 `spring-cloud-dependencies` 依赖
>   * 后端进程间用 `RestTemplate`进行通信
>   * 微服务用 `SpringCloud` 来实现，但由于这个项目的并发量不大，所有跟 `SpingCloud` 的直接关系不是很大，很多东西都没用上


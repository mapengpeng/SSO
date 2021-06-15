# mapp-SSO

- 基于cookie实现，可跨域的单点登录；
- 提供缓存抽象模板，可自定义缓存实现，实现分布式部署；
- http方式实现单点退出，实现任意一个系统退出，其余子系统都会退出；
- 基于filter实现，子系统接入简单
- sso拦截器提供Ant路径匹配规则
- 子系统需要在sso端认证后才可以接入

### sso-server  sso服务端
### sso-client1  接入客户端demo

## 登录页![输入图片说明](https://images.gitee.com/uploads/images/2021/0615/112913_dbc438fe_926790.png "登录页.png")
## sso主页
![输入图片说明](https://images.gitee.com/uploads/images/2021/0615/112941_76718434_926790.png "主页.png")
## 子系统一
![输入图片说明](https://images.gitee.com/uploads/images/2021/0615/113017_bf8d599c_926790.png "sso1.png")

# 安卓快速开发框架

#### 一. 框架代码说明：
> 1. 集成java-MVP模式，关于如何使用MVP参考examplemodule.mvp包；
> 2. 集成统一的gradle文件，对第三方库的依赖以及依赖版本进行了统一管理；
> 3. 已经实例话的Application，已经初始化OkGo框架，初始化ELog日志输出系统；
> 4. 在Application中添加GlobarConstantBean，对所有的全局变量进行统一管理；
> 5. ELog日志输出统一管理，通过 "ELog.setShowLog()"设置是否在控制台上输出日志；
> 6. 集成Config统一管理类（Path: globar package下）；
> 7. 集成网络广播的通知，并对当前的网络状态进行全局储存
> 8. BaseActivity 与 BaseNetActivity，当你的Activity需要管理到网络状态然后对某些UI进行改变时候，可以
     extends BaseNetActivity，接着重写receivingNetworkState(int style)即可。

#### 二、 工具类说明
类名 | 说明
:-: | :-
ActivityManageUtil | activity的统一管理
AppDataCache | sharepreferences的储存
AppUtils | app使用工具
BitmapUtil | bitmap工具
BytesUtil | 字节工具
CrashHandle | 腾讯bugly本地日志打印工具（有些环境是内网情况，无法及时上传bug资料到bugly）
ELog | 控制台调试日志
FileUtil | 与文件系统有关的工具类
GsonUtil | json与object之间的转换
ScreenUtil | 屏幕分辨率工具类
SoftKeyboardUtils | 软键盘工具类
StringUtil | 字符串工具类
TimeUtil | 时间戳工具类
ToastUtil | 单例模式的Toast
UiUtil | 试图工具类

#### 三、
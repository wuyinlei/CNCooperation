# 开源项目CNCooperation相关说明

标签（空格分隔）： 开源项目

---
###首先来波图片看看这个app将会实现些什么功能和已经实现的功能
![此处输入图片的描述][1]
![此处输入图片的描述][2]
![此处输入图片的描述][3]
![此处输入图片的描述][4]
![此处输入图片的描述][5]

###项目将要实现的功能
####首页
首页将会实现一个购物的首页(预计将会实现的和小米商城首页类似,包含推荐、手机、智能、电视、电脑、生活周边等几大tab,关于UI...那就照抄小米商城来吧)

####试水
这个栏目,准备实现的是一些新闻、段子、好看的图片、有趣的视频,可能会用到内涵段子的公开API(包含上面提到的内容)

####直播
直播界面会实现直播和点播功能

####购物车
就是一般的购物车逻辑,主要是对于主页的商品的购买等相关逻辑,应该会加入支付宝和微信支付(看能不能申请,如果不能会加入其它类型的第三方支付)

####我的界面
应该会实现我的信息管理、订单、地址管理、设置、签到、收藏等相关逻辑、还有就是加入朋友圈的功能,后期会加入IM即时通讯功能。

###已经实现的内容

* 注册功能已经实现
* 发布朋友圈功能已经实现(发布纯文字、文字加图片、纯图片形式)
* 朋友圈查看功能已经实现(类似微信朋友圈功能)

###近期将会实现的功能
* 朋友圈的动态收藏实现、点赞
* 朋友圈动态的评论实现
* 首页栏目实现
(因为都是工作之余时间写的代码逻辑,周末应该更新的快些)

###运用的技术
* MVP+OkHttp+Retrofit+RxJava(1.x版本,本来使用2.0版本,但奈何和Bmob有冲突)
* 同时也会加入一些自己认为的不错的开源库(类似表情键盘无缝切换,图片压缩,上下拉加载框架等。。。。)
* 采用分包实现(app--->主要UI层,common--->放置一些公共的控件和变量,factory--->主要的逻辑层也就是Model层和MVP中的Presenter层,cnlang--->主要对应的是string字符串和style主题层次)
* 代码尽量做到简便,会注意到相应的文字注释,遵循MD风格实现


###项目分层说明
####app模块
这全局唯一的app启动入口,也就是VIEW层次的逻辑放置处,在factory层处理好的数据返回到VIEW层次,app只是用来绑定UI控件和相关的UI更新

####factory模块
这个是各种业务逻辑管理层次,mvp模式的p层和m层逻辑

####common模块
这个是全局使用的公共类或者一些工具类放置处

####cnlang模块
专门放置strings、dimens、colors.xml文件

###交流
喜欢交流,如果对于这个项目感兴趣或者有疑问或者相关问题的话,可以进群进行讨论(完全自己实现哈哈哈,自己能力有限,有些逻辑优化并不是很完善,可以多多交流)

* 群号:136471108   
* 群号:136471108
* 群号:136471108

  [1]: http://upload-images.jianshu.io/upload_images/1316820-6babc3093668a69c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50
  [2]: http://upload-images.jianshu.io/upload_images/1316820-33cee80c7d266ea3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50
  [3]: http://upload-images.jianshu.io/upload_images/1316820-367da62479f07b75.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50
  [4]: http://upload-images.jianshu.io/upload_images/1316820-6694e234170f40fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50
  [5]: http://upload-images.jianshu.io/upload_images/1316820-5139dc0c4a6e4045.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1080/q/50

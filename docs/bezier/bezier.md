# Photoshop矢量图转Android自定义View



### 是什么？

贝塞尔曲线(Bézier curve)，又称贝兹曲线或贝济埃曲线，是应用于二维图形应用程序的数学曲线。一般的矢量图形软件通过它来精确画出曲线，我们在绘图工具上看到的[钢笔工具](http://bezier.method.ac/)就是来做这种矢量曲线的。


Android从API1起就支持贝塞尔曲线，实现方式是借助android.graphics.Path类。

一般来说，开发者只考虑二阶贝塞尔曲线和三阶贝塞尔曲线，对于再高阶的贝塞尔曲线，通常可以将曲线拆分成多个低阶的贝塞尔曲线，也就是所谓的降阶操作。


贝塞尔曲线 | 对应的方法 | 演示动画
---|---|---
一阶贝塞尔曲线（直线） | ```path.lineTo(end.x, end.y)``` | ![一阶贝塞尔曲线](http://double0291.github.io/BezierBase/bezier_anim_1.gif)
二阶贝塞尔曲线（抛物线） | ```path.cubicTo(control.x, control.y, end.x, end.y)``` | ![二阶贝塞尔曲线](http://double0291.github.io/BezierBase/bezier_anim_2.gif)
三阶贝塞尔曲线 | ```path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y)``` | ![三阶贝塞尔曲线](https://user-gold-cdn.xitu.io/2017/8/23/bd9b5ba0ef55d80f6ed3cc395ca95112?imageView2/0/w/1280/h/960)




### 做什么？
- 线条平滑过渡
  ![iamge](http://7rf34y.com2.z0.glb.qiniucdn.com/c/5b0ff33025cd16d1cfbf9f176763d0a1)
- 绘制复杂图形
  ![iamge](http://www.neuni-lab.com/wp-content/uploads/2016/12/neuni-lab_webwxgetmsgimg-18-1024x409.png)

### 怎么做？
- 绘制复杂图形



### 理论知识
- [贝塞尔插值-一种非常简单的多边形平滑方法](http://blog.csdn.net/microchenhong/article/details/6316332)
  ![iamge](http://upload-images.jianshu.io/upload_images/2114436-af403f13333d5770.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- [Bézier 求贝塞尔曲线控制点](https://github.com/OCNYang/ContourView/wiki/B%C3%A9zier-%E6%B1%82%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF%E6%8E%A7%E5%88%B6%E7%82%B9)
  ![iamge](https://camo.githubusercontent.com/84d9821dcf5fb1163ef78e83c1c8e8c33b02393e/687474703a2f2f6f6262753672316d692e626b742e636c6f7564646e2e636f6d2f6769746875622f636f6e746f7572766965772f42657a69657230322e676966)

- [抛物线三切线定理 / de Casteljau算法](http://netclass.csu.edu.cn/NCourse/hep089/Chapter3/CG_Txt_3_016.htm)
  ![iamge](http://netclass.csu.edu.cn/NCourse/hep089/Chapter3/312img/CG_Gif_3_010.gif)



### 使用方法

```
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // P1-->P2
    // 初始化
    start.x = 266;
    start.y = 80;
    end.x = 573;
    end.y = 128;
    control1.x = 359;
    control1.y = 15;
    control2.x = 503;
    control2.y = 31;
        
    path.moveTo(start.x, start.y); // 起点
    path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y); // 三阶贝塞尔曲线

    canvas.drawPath(path, paint);
}

```

### 一个栗子

下图使用Photoshop钢笔工具绘制，其中图片大小为761*544px。

![row 1 col 2](image/bezier.png)


*数据点*P1到P2，其*控制点*分别为C11、C21，其各自坐标如图所示。

其它数据点P2-->P3、P3-->P4、P4-->P1，对应的控制点分别为C22和C31、C32和C41、C42和C12。


Ps：如何从Potoshop图获取各点坐标？

首先，窗口--信息（快捷键F8），打开信息窗口，然后使用鼠标移动到各点即可看到具体坐标。

另外，信息窗口默认显示的坐标是毫米，需要从信息窗口右边小三角点击，然后弹出的面板选择--面板选项，在鼠标坐标栏目选择"像素"。


```
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // P1-->P2
    // 初始化
    start.x = 266;
    start.y = 80;
    end.x = 573;
    end.y = 128;
    control1.x = 359;
    control1.y = 15;
    control2.x = 503;
    control2.y = 31;
        
    path.moveTo(start.x, start.y); // 起点
    path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y); // 三阶贝塞尔曲线

    canvas.drawPath(path, paint);
}

```

最后结果：

![row 1 col 2](image/android_bezier.png)





### 参考资料
- [贝塞尔曲线原理介绍及基础使用
](http://double0291.github.io/2015/10/09/bezierBase/) （```腾讯聊天界面实现，注意：由于原图比较大，代码绘制过程中，坐标根据设计图中的坐标乘以了3```）
- [eclipse_xu - 贝塞尔曲线开发的艺术](http://blog.csdn.net/eclipsexys/article/details/51956908)（```Android群英传作者，贝塞尔曲线在线工具+曲线图变圆应用```）
- [德卡斯特里奥算法——找到Bezier曲线上的一个点](http://blog.csdn.net/venshine/article/details/51750906)
-  通过 de Casteljau 算法绘制贝塞尔曲线，并计算它的切线，实现 1-7 阶贝塞尔曲线的形成动画。 http://blog.csdn.net/u014608640/article/details/53063800
- [android 贝塞尔曲线的应用](http://www.jianshu.com/p/c0d7ad796cee)

- [练习图1-蝙蝠侠](http://www.guokr.com/post/56597/)
- [练习图1-蝙蝠侠](https://www.google.com.hk/search?q=%E8%9D%99%E8%9D%A0%E4%BE%A0&safe=strict&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiSx5Gns8TWAhXBoJQKHZVDDPgQ_AUICigB&biw=1920&bih=1069)
- [练习图2-卡通](http://www.qqread.com/photoshop/c192115003.html)
- [练习图3-QQ](http://www.wodnaifen.com/doeoea.html)


这里需要注意的是要处理一下起始点和结束点。上面设置的为（0，0）和（250，0）；这两个点是原有的点集没有的，根据需要可以适当设置，会影响到第一段和最后一段曲线的转向。

http://www.jianshu.com/p/55099e3a2899


Android绘制N阶Bezier曲线

https://venshine.github.io/2016/07/10/Android%E7%BB%98%E5%88%B6N%E9%98%B6Bezier%E6%9B%B2%E7%BA%BF/


贝塞尔曲线原理分析及其Android的实现
本文主要内容为贝塞尔曲线原理解析并用 SurfaceView 实现其展示动画
http://www.jianshu.com/p/1af5c3655fa3

[转载]三次贝塞尔曲线的控制点 
有若干条连续的直线段，要对某些线段做拟合，让整体路径更平滑。
贝塞尔曲线通过若干点来生成光滑曲线。我的需求是用一条贝塞尔曲线代替一条线段，起始点已经确定。
三次贝塞尔曲线有4个控制点，其中2个是起始点不用求，中间2个控制点（所在直线）的求法有两种：
一种是角平分线的垂线；
一种是线段中点连线的平行线。
http://blog.sina.com.cn/s/blog_640531380100x5vq.html


Bezier曲线曲面算法
https://www.tanglei.name/blog/bezier-curve-and-surface.html


【译】德卡斯特里奥算法——找到Bezier曲线上的一个点
https://venshine.github.io/2016/07/10/%E3%80%90%E8%AF%91%E3%80%91%E5%BE%B7%E5%8D%A1%E6%96%AF%E7%89%B9%E9%87%8C%E5%A5%A5%E7%AE%97%E6%B3%95%E2%80%94%E2%80%94%E6%89%BE%E5%88%B0Bezier%E6%9B%B2%E7%BA%BF%E4%B8%8A%E7%9A%84%E4%B8%80%E4%B8%AA%E7%82%B9/
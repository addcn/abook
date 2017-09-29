# animation



自定义View的动画是一帧一帧的过程。对于我们的例子，这意味着你需要使圆点的半径从小变到大，你需要一点一点的增加其半径值，并在每一步调用 invalidata() 重绘视图。

ValueAnimator是实现自定义View动画时最好的帮手。这个类可以帮助你计算从开始到结束的属性值，必要时还可以使用Interpolator改变动画执行的速度。

http://chen-wei.me/2017/02/16/%E6%95%B4%E4%B8%AA%E8%87%AA%E5%AE%9A%E4%B9%89View/



设置属性动画

由于动画需要一组0-1的数据
这里我们借用属性动画提供给我们的数值来实现动画。
http://www.jianshu.com/p/8bc9a4af771f

### 参考资料
- [属性动画连续输出值分析](http://www.idtkm.com/2016/11/10/F1%E3%80%81Property%20Animation/)

http://liandongyang.coding.me/post/Android%E5%B1%9E%E6%80%A7%E5%8A%A8%E7%94%BBcancel%E5%92%8Cend%E8%A7%A3%E6%9E%90/
http://will4it.com/android/2015/07/05/on-animation-end-crash/


我们是如何实现漂亮动画的－列车飞驰的加载动画（貌似使用了自定义插值器！！！！！！！！！）
http://android.jobbole.com/85075/




自定义view————Animation实践篇
https://madreain.github.io/2017/07/26/20170726/


Android属性动画完全解析(上)，初识属性动画的基本用法
http://blog.csdn.net/guolin_blog/article/details/43536355


当数学遇上动画：讲述ValueAnimator、TypeEvaluator和TimeInterpolator之间的恩恩怨怨(1)
http://javayhu.me/blog/2016/05/26/when-math-meets-android-animation-1/
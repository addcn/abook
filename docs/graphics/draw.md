# Draw


## 二.View的坐标系

**注意：View的坐标系统是相对于父控件而言的.**

``` java
  getTop();       //获取子View左上角距父View顶部的距离
  getLeft();      //获取子View左上角距父View左侧的距离
  getBottom();    //获取子View右下角距父View顶部的距离
  getRight();     //获取子View右下角距父View左侧的距离
```

**如下图所示：**

![](http://ww2.sinaimg.cn/large/005Xtdi2gw1f1qzqwvkkbj308c0dwgm9.jpg)


## 三.MotionEvent中 get 和 getRaw 的区别

```
    event.getX();       //触摸点相对于其所在组件坐标系的坐标
    event.getY();

    event.getRawX();    //触摸点相对于屏幕默认坐标系的坐标
    event.getRawY();

```

**如下图所示：**

> PS:其中相同颜色的内容是对应的，其中为了显示方便，蓝色箭头向左稍微偏移了一点.

![](http://ww1.sinaimg.cn/large/005Xtdi2jw1f1r2bdlqhbj308c0dwwew.jpg)






### 简要介绍画布的操作:

| 相关操作      | 简要介绍        |
| --------- | ----------- |
| save      | 保存当前画布状态    |
| restore   | 回滚到上一次保存的状态 |
| translate | 相对于当前位置位移   |
| rotate    | 旋转          |

https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B02%5DCanvas_BasicGraphics.md




## 一.Canvas的常用操作速查表

| 操作类型       | 相关API                                    | 备注                                       |
| ---------- | ---------------------------------------- | ---------------------------------------- |
| 画布快照       | save, restore, saveLayerXxx, restoreToCount, getSaveCount | 依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数 |
| 画布变换       | translate, scale, rotate, skew           | 依次为 位移、缩放、 旋转、错切                         |
| Matrix(矩阵) | getMatrix, setMatrix, concat             | 实际上画布的位移，缩放等操作的都是图像矩阵Matrix， 只不过Matrix比较难以理解和使用，故封装了一些常用的方法。 |


#### ⑴位移(translate)

 translate是坐标系的移动，可以为图形绘制选择一个合适的坐标系。
 **请注意，位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动**，如下：
``` java
        // 省略了创建画笔的代码
        
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
```

<img src="http://ww3.sinaimg.cn/large/005Xtdi2jw1f2f1ph46qaj30u01hcgm3.jpg" width="300"/>  

我们首先将坐标系移动一段距离绘制一个圆形，之后再移动一段距离绘制一个圆形，<b>两次移动是可叠加的</b>。







### 快照(save)和回滚(restore)

画布的操作是不可逆的，而且很多画布操作会影响后续的步骤，例如第一个例子，两个圆形都是在坐标原点绘制的，而因为坐标系的移动绘制出来的实际位置不同。所以会对画布的一些状态进行保存和回滚。

*相关的API:*

| 相关API          | 简介                             |
| -------------- | ------------------------------ |
| save           | 把当前的画布的状态进行保存，然后放入特定的栈中        |
| saveLayerXxx   | 新建一个图层，并放入特定的栈中                |
| restore        | 把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布  |
| restoreToCount | 弹出指定位置及其以上所有的状态，并按照指定位置的状态进行恢复 |
| getSaveCount   | 获取栈中内容的数量(即保存次数)               |

##### 常用格式
虽然关于状态的保存和回滚啰嗦了不少，不过大多数情况下只需要记住下面的步骤就可以了：
``` java
   save();      //保存状态
   ...          //具体操作
   restore();   //回滚到之前的状态
```
这种方式也是最简单和最容易理解的使用方法。


Canvas之画布操作
https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B03%5DCanvas_Convert.md
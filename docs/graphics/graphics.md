# graphics


## 自定义view速查表

### Canvas常用操作速查表

功能 | API | 说明
---|---|---
| 绘制颜色       | drawColor <br /> drawRGB, drawARGB             | 使用单一颜色填充整个画布|
| 绘制基本形状     | drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc | 依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧|
| 绘制图片       | drawBitmap, drawPicture                  | 绘制位图和图片 |
| 绘制文本       | drawText,    drawPosText, drawTextOnPath | 依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字|
| 绘制路径       | drawPath                                 | 绘制路径，绘制贝塞尔曲线时也需要用到该函数 |
| 顶点操作       | drawVertices, drawBitmapMesh             | 通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用 |
| 画布剪裁       | clipPath,    clipRect                    | 设置画布的显示区域                                |
| 画布快照       | save, restore, saveLayerXxx, restoreToCount, getSaveCount | 依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 会滚到指定状态、 获取保存次数|
| 画布变换       | translate, scale, rotate, skew           | 依次为 位移、缩放、 旋转、错切|
| Matrix(矩阵) | getMatrix, setMatrix, concat             | 实际画布的位移，缩放等操作的都是图像矩阵Matrix，只不过Matrix比较难以理解和使用，故封装了一些常用的方法。|

功能 | API | 说明
---|---|---
| 基础方法       | getDensity, getWidth, getHeight，getDrawFilter，isHardwareAccelerated(API 11)，getMaximumBitmapWidth，getMaximumBitmapHeight，getDensity，quickReject，isOpaque，setBitmap，setDrawFilter | 使用单一颜色填充画布                               |
| 绘制颜色       | drawColor, drawRGB, drawARGB，drawPaint   | 使用单一颜色填充画布                               |
| 绘制基本形状     | drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc | 依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧                  |
| 绘制图片       | drawBitmap, drawPicture                  | 绘制位图和图片                                  |
| 绘制文本       | drawText,    drawPosText, drawTextOnPath | 依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字          |
| 绘制路径       | drawPath                                 | 绘制路径，绘制贝塞尔曲线时也需要用到该函数                    |
| 顶点操作       | drawVertices, drawBitmapMesh             | 通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用 |
| 画布剪裁       | clipPath,    clipRect， clipRegion，getClipBounds | 画布剪裁相关方法                                 |
| 画布快照       | save, restore, saveLayer, saveLayerXxx, restoreToCount, getSaveCount | 依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数 |
| 画布变换       | translate, scale, rotate, skew           | 依次为 位移、缩放、 旋转、错切                         |
| Matrix(矩阵) | getMatrix, setMatrix, concat             | 实际画布的位移，缩放等操作的都是图像矩阵Matrix，只不过Matrix比较难以理解和使用，故封装了一些常用的方法。 |


Path常用操作速查表

API21以上

功能 | API | 说明
---|---|---
| 移动起点	| moveTo	| 移动下一次操作的起点位置
| 设置终点	| setLastPoint	| 重置当前path中最后一个点位置，如果在绘制之前调用，效果和moveTo相同
| 连接直线	| lineTo	| 添加上一个点到当前点之间的直线到Path
| 闭合路径	| close	| 连接第一个点连接到最后一个点，形成一个闭合区域
| 添加内容	| addRect, addRoundRect, addOval, addCircle, addPath, addArc, arcTo	| 添加(矩形， 圆角矩形， 椭圆， 圆， 路径， 圆弧) 到当前Path (注意addArc和arcTo的区别)
| 是否为空	| isEmpty	| 判断Path是否为空
| 是否为矩形	| isRect	| 判断path是否是一个矩形
| 替换路径	| set	| 用新的路径替换到当前路径所有内容
| 偏移路径	| offset	| 对当前路径之前的操作进行偏移(不会影响之后的操作)
| 贝塞尔曲线	| quadTo, cubicTo	| 分别为二次和三次贝塞尔曲线的方法
| rXxx方法	| rMoveTo, rLineTo, rQuadTo, rCubicTo	| 不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
| 填充模式	| setFillType, getFillType, isInverseFillType, toggleInverseFillType	| 设置,获取,判断和切换填充模式
| 提示方法	| incReserve	| 提示Path还有多少个点等待加入(这个方法貌似会让Path优化存储结构)
| 布尔操作(API19)	| op	| 对两个Path进行布尔运算(即取交集、并集等操作)
| 计算边界	| computeBounds	| 计算Path的边界
| 重置路径	| reset, rewind	| 清除Path中的内容。reset不保留内部数据结构，但会保留FillType. rewind会保留内部的数据结构，但不保留FillType
| 矩阵操作	| transform	| 矩阵变换


Matrix常用操作速查表

功能 | API | 说明
---|---|---
| 基本方法	| equals hashCode toString toShortString | 比较、 获取哈希值、 转换为字符串
| 数值操作	| set reset setValues getValues	| 设置、 重置、 设置数值、 获取数值
| 数值计算	| mapPoints mapRadius mapRect mapVectors	| 计算变换后的数值
| 设置(set)	| setConcat setRotate setScale setSkew setTranslate	| 设置变换
| 前乘(pre)	| preConcat preRotate preScale preSkew preTranslate	| 前乘变换
| 后乘(post)| postConcat postRotate postScale postSkew postTranslate	| 后乘变换
| 特殊方法	| setPolyToPoly setRectToRect rectStaysRect setSinCos	| 一些特殊操作
| 矩阵相关	| invert isAffine(API21) isIdentity	| 求逆矩阵、 是否为仿射矩阵、 是否为单位矩阵 ...



### 参考资料
- [自定义view总结](https://juejin.im/post/599d2b2e518825242238d4f6)
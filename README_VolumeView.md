# VolumeView
VolumeView一个进度条控件，也可以视为一个音量控件，整体效果如下：<br>
*为了看到效果就给控件加了背景*
![image](https://github.com/WRainbow/Bed-Of-ScreenShot/blob/master/Gif/VolumeView.gif "VolumeView.gif")
### 使用
可以直接把测试Demo中widget包下的VolumeView文件直接拷贝即可，其中在文件中涉及到的接口、工具类方法等也需要复制（也可以把涉及到的方法复制到VolumeView中）。<br>
### 提供的功能
###### xml代码设置
1. `app:touchExpandable="false"`：设置响应范围，参照`setTouchExpandable`
2. `app:clickValid="false"`：设置是否定位到点击位置，参照`setClickValid`
3. `app:viewGravity="CENTER"`：设置绘制区域位置，参照`setViewGravity`
4. `app:fullWidth="false"`：设置绘制区域左右是否留白，参照`setFullWidth`
5. `app:colorBackground="@color/colorAccent"`：设置绘制区域背景色，参照`setColorBackground`
6. `app:colorForeground="@color/colorAccent"`：设置绘制区域前景色，参照`setColorForeground`
7. `app:colorBall="@color/colorAccent"`：设置小球颜色，参照`setColorBall`
8. `app:promptly="true"`：手动设置进度时是否进行回调，参照`setPromptly`
9. `app:blankDistance="20dp"`：设置留白距离，参照`setBlankHeight`
10. `app:viewHeight="25dp"`：设置绘制区域高度，参照`setViewHeight`
11. `app:percent="50"`：设置初始进度值
###### Java代码设置
VolumeView作为进度条控件，可以自行设置进度和进度回调，支持手动拖拽和点击定位，自行设置控件高度等，下面是详细的设置方法：<br>
1. `void setTouchExpandable(boolean touchExpandable)`：设置是否在整个View上都可以进行拖拽改变进度（音量），因为整个控件的大小和显示进度的大小往往是不同的，因此，VolumeView提供了可自行设置响应范围，当控件很大而音量进度条较小时，可以调用该方法扩大响应范围；
2. `void setClickValid(boolean clickValid)`：设置是否响应点击定位进度。默认点击控件时不会自动定位到点击位置。
3. `void setViewGravity(int gravity)`：设置音量进度条在控件中的绘制位置，当控件大于音量控件绘制区域时可以自行设定绘制位置，支持`Gravity.TOP`、`Gravity.CENTER`、`Gravity.BOTTOM`三种，默认为`Gravity.CENTER`；
4. `void setFullWidth(boolean fullWidth)`：设置显示是否宽度占满整个控件，默认VolumeView左右会留有空白，如果想要设置宽度占满可以使用此方法；
5. `void setBlankHeight(int dpValue)`：设置VolumeView左右留白的距离，默认为音量进度条的绘制高度，当VolumeView设置宽度不占满时可以使用此方法调节左右间距，如果音量进度条的宽度占满时此方法无效；
6. `void setViewHeight(int dpValue)`：设置VolumeView的绘制高度；
7. `void setColorBackground(@ColorInt int mColorBackground)`：设置音量进度条背景色；
8. `void setColorForeground(@ColorInt int mColorForeground)`：设置音量进度条前景色（已到达进度）；
9. `void setColorBall(@ColorInt int mColorBall)`：设置音量进度条小球颜色；
10. `void setPercent(int percent)`：设置进度条当前进度；
11. `int getPercent()`：获取当前进度条进度；
12. `void setPromptly(boolean promptly)`：在使用很多选择控件时，设置选择回调监听后，如果手动设置选择（进度）后也会触发回调，有时并不希望手动设置时会引起回调，此方法可以设置是否回调响应手动设置进度；
13. `void setProgressListener(IProgressListener mProgressListener)`：设置进度回调监听；
14. `void show()`：在使用代码设置VolumeView的基本属性时（绘制高度、绘制位置、点击范围等）并不会立马让VolumeView做出反应，在经过一系列设置后需要调用此方法生效。
### 帮助
如果这个控件刚好可以帮到你，欢迎提出使用时遇到的问题...
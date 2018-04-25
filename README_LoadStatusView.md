# LoadStatusView
LoadStatusView一个根据不同状态加载不同布局或图片来给用户以提示。

在项目中总会遇到网络加载时，根据不同加载结果给用户提示；当加载成功时显示数据，但是当加载失败或者无数据时需要给用户提示。每次在项目中都需要写上好几个布局然后根据加载结果进行控制，虽然简单，但是麻烦。

LoadStatusView主要涉及场景就是多个View切换时，控制显隐比较麻烦，单独封装之后直接调用方法即可实现，省去了很多代码逻辑。LoadStatusView还可以针对开屏广告、引导页等的切换进行设置，然后根据不同时机显示不同View

*为了看到效果就给控件加了背景*

![image](https://github.com/WRainbow/Bed-Of-ScreenShot/blob/master/Gif/LoadStatusView.gif "VolumeView.gif")

### 使用
可以直接把测试Demo中widget包下的LoadStatusView文件直接拷贝即可，其中在文件中涉及到的文件也需要复制。

### 提供的功能
###### xml代码设置
LoadStatusView默认提供了几种显示状态，在xml中可以设置这几种状态对应显示的图片或者布局

1. `app:img_no_data="@minmap/ic_no_data"`：设置无数据时的显示图片，参照`addStatusAndImg`
2. `app:img_no_net="@minmap/ic_no_net"`：设置无网络时显示图片，参照`addStatusAndImg`
3. `app:img_load_error="@minmap/ic_load_error"`：设置加载失败时显示图片，参照`addStatusAndImg`
4. `app:view_no_data="@layout/layout_no_data"`：设置无数据时的显示布局，参照`addStatusAndView`
5. `app:view_no_net="@layout/layout_no_net"`：设置无网络时显示布局，参照`addStatusAndView`
6. `app:view_load_error="@layout/layout_load_error"`：设置加载失败时显示布局，参照`addStatusAndView`

###### Java代码设置
1. `void addStatusAndView(@IdRes int status, View show)`：设置加载状态和对应显示的View对象；
2. `void addStatusAndView(@IdRes int status, int resId)`：设置加载状态和对应的显示布局文件；
3. `void addStatusAndImg(@IdRes int status, @DrawableRes int resource)`：设置加载状态和对应的显示图片资源
4. `void showViewByStatus(@IdRes int status)`：根据状态显示对应View
6. `void showImgByStatus(@IdRes int status)`：根据状态显示对应的图片
7. `void hideLoadStauts()`：隐藏LoadStatusView
### 帮助
如果这个控件刚好可以帮到你，欢迎提出使用时遇到的问题...
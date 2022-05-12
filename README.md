# WorkManager
- ## WorkManager作用
  WorkManager是[Android Jetpack](https://developer.android.com/jetpack/androidx/releases/window)的一部分，是一个用来执行后台任务的框架。有这些特点：
1. 执行后台任务，如上传、下载、同步数据等，特点是即使在离开应用应用挂起会被杀死也可以执行。
2. 支持一次性和重复任务，可以设置重复轮询执行时间
3. 支持添加约束条件，如是否联网、是否充电、是否空闲等
4. 支持链式工作请求
5. 支持到API 14
6. 内部对电量进行了优化
- ## WorkManager角色
1. Worker : 需要执行的任务，使用时继承Worker，在doWork()方法中执行任务
2. WorkRequest：执行一项单一的任务默认子类有单一任务的OneTimeWorkRequest和重复任务PeriodicWorkRequest。WorkReques都有一个自动生成的唯一ID
* OneTimeWorkRequest

``  val workRequest1 = OneTimeWorkRequest.Builder(WorkMore1::class.java).build() ``
* PeriodicWorkRequest

``  val periodicWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(WorkMore1::class.java, 10, TimeUnit.MINUTES).build() ``
3. WorkManager：管理工作请求，将WorkRequest对象传递给WorkManager的任务队列
4. WorkStatus：任务状态

- ## WorkManager的使用
1. 导入： implementation "androidx.work:work-runtime:2.7.1"  最新版本地址[参考这里](https://developer.android.com/jetpack/androidx/releases/work)
2. 创建自己的Work类继承自Work，在dowork中实现执行任务代码
3. 通过build模式创建WorkRequest
4. 通过WorkManager.getInstance(context)拿到WorkManager实例调用enqueue方法传入WorkRequest
5.添加输入和输出data 
`Data.Builder builder = new Data.Builder()`
- ## WorkManager源码分析


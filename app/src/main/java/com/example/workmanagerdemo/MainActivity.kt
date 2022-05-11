package com.example.workmanagerdemo

import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.workmanagerdemo.workmanager.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() , SharedPreferences.OnSharedPreferenceChangeListener{

    private var checkButton: TextView? = null
    private lateinit var mSp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkButton = findViewById(R.id.checkButton)

        mSp = getSharedPreferences(CheckWork.SP_NAME, MODE_PRIVATE)
        mSp.registerOnSharedPreferenceChangeListener(this)
        upDateUI()
    }

    fun upDateUI() {
        val value = mSp.getInt(CheckWork.SP_KEY,0)
        checkButton?.text = "验证后台任务--$value"
    }

    //单任务：发送给work数据 并收到work的执行结果
    fun onTestButtonClick(view: View) {
        val sendData = Data.Builder().putString("name", "张三").build()
        val workRequest =
            OneTimeWorkRequest.Builder(MyWork::class.java).setInputData(sendData).build()
        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(this) {
                if (it.state.isFinished) {
                    Log.d(TAG, "传回来的数据---${it.outputData.getString("name")} ")
                }
            }
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    //多任务：
    fun onTestButtonClick2(view: View) {
        val workRequest1 = OneTimeWorkRequest.Builder(WorkMore1::class.java).build()
        val workRequest2 = OneTimeWorkRequest.Builder(WorkMore2::class.java).build()
        val workRequest3 = OneTimeWorkRequest.Builder(WorkMore3::class.java).build()

        //顺序执行1 2 3
        WorkManager.getInstance(this)
            .beginWith(workRequest1)
            .then(workRequest2)
            .then(workRequest3)
            .enqueue()

        /*     val oneTimeWorkRequests: MutableList<OneTimeWorkRequest> = ArrayList()
             oneTimeWorkRequests.add(workRequest1)
             oneTimeWorkRequests.add(workRequest2)

             WorkManager.getInstance(this)
                 .beginWith(oneTimeWorkRequests)
                 .then(workRequest3)
                 .enqueue()*/

    }

    //重复后台任务
    fun onTestButtonClick3(view: View) {
        //设置重复执行间隔 参数要最少15分钟
        val periodicWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(WorkMore1::class.java, 10, TimeUnit.MINUTES).build()

        WorkManager.getInstance(this).enqueue(periodicWorkRequest)

        //取消
//        WorkManager.getInstance(this).cancelWorkById(periodicWorkRequest.id)
    }

    //约束后台任务
    @RequiresApi(Build.VERSION_CODES.M)
    fun onTestButtonClick4(view: View) {

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)  //联网状态
//            .setRequiresCharging(true)  //正在充电
//            .setRequiresDeviceIdle(true)    //空闲中
            .build()

        /**
         * setRequiredNetworkType：网络连接设置
         * setRequiresBatteryNotLow：是否为低电量时运行 默认false
         * setRequiresCharging：是否要插入设备（接入电源），默认false
         * setRequiresDeviceIdle：设备是否为空闲，默认false
         * setRequiresStorageNotLow：设备可用存储是否不低于临界阈值
         */

        val request = OneTimeWorkRequest.Builder(WorkMore1::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(request)
    }

    //验证后台任务
    fun onTestButtonClick5(view: View) {
        val constraints: Constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)  //联网状态
            .build()

        val request = OneTimeWorkRequest.Builder(CheckWork::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(request)
    }

    //sp文件发生变化的监听
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) = this.upDateUI()
}
package com.example.workmanagerdemo.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CheckWork(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters)  {

    companion object {
        const val SP_NAME = "work_manager_demo_sp"
        const val SP_KEY = "sp_key"
    }

    //模拟后台任务
    override fun doWork(): Result {
        try {
            Thread.sleep(5000)
        }catch (e: Exception) {
            e.printStackTrace()
        }

        val sp = applicationContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        var spValue = sp.getInt(SP_KEY,0)
        sp.edit().putInt(SP_KEY, ++spValue).apply()
        Log.d(TAG, "CheckWork doWork: spValue--${spValue} ")
        return Result.success()
    }
}
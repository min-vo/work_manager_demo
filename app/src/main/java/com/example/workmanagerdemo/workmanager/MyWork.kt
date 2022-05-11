package com.example.workmanagerdemo.workmanager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWork(context: Context,val workerParameters: WorkerParameters): Worker(context, workerParameters) {

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        Log.d(TAG, "MyWork doWork: run started ... ")
        //
        val inputData = workerParameters.inputData.getString("name")
        Log.d(TAG, "MyWork doWork: 接收activity参数name$inputData ")
        //返回参数
        val outputData = Data.Builder().putString("name","${inputData}的家 ").build()
        return Result.Success(outputData)
    }



}

const val TAG = "MyWork"
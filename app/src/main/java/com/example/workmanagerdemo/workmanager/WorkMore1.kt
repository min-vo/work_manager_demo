package com.example.workmanagerdemo.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkMore1(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {

    override fun doWork(): Result {
        try {
            Thread.sleep(5000)
        } catch (e:Exception){

        }
        Log.d(TAG, "WorkMore1 doWork: run started ... ")
        return Result.success()
    }
}

class WorkMore2(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "WorkMore2 doWork: run started ... ")
        return Result.success()
    }
}

class WorkMore3(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {

    override fun doWork(): Result {
        Log.d(TAG, "WorkMore3 doWork: run started ... ")
        return Result.success()
    }
}
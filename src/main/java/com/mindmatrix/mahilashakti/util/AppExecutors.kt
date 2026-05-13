package com.mindmatrix.mahilashakti.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

object AppExecutors {
    private val disk = Executors.newSingleThreadExecutor()
    private val main = Handler(Looper.getMainLooper())

    fun io(block: () -> Unit) {
        disk.execute(block)
    }

    fun main(block: () -> Unit) {
        main.post(block)
    }
}

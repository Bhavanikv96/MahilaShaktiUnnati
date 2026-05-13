package com.mindmatrix.mahilashakti

import android.app.Application
import com.mindmatrix.mahilashakti.data.AppDatabase

class MahilaShaktiApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
}

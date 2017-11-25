package com.github.rozag.kueue.android.sample

import android.app.Application
import com.github.rozag.kueue.Kueue
import com.github.rozag.kueue.android.MainThreadExecutor
import java.util.concurrent.Executors

class SampleApp : Application() {

    companion object {
        val queue = Kueue(
                workerExecutor = Executors.newSingleThreadExecutor(),
                callbackExecutor = MainThreadExecutor()
        )
    }

}
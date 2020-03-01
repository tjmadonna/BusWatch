package com.madonnaapps.buswatch.config.thread

import com.madonnaapps.buswatch.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UIThread @Inject constructor() : PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}
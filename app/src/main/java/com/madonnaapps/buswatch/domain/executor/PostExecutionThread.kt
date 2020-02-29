package com.madonnaapps.buswatch.domain.executor

import io.reactivex.Scheduler

// Added so we can inject scheduler with dagger
interface PostExecutionThread {
    val scheduler: Scheduler
}
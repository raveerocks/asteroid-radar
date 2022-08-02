package io.raveerocks.asteroidradar.jobs

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import io.raveerocks.asteroidradar.jobs.workers.RefreshCacheWorker
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test

class RefreshCacheWorkerTest {

    private lateinit var workManager : WorkManager

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        workManager = WorkManager.getInstance(context)
    }

    @Test
    @Throws(Exception::class)
    fun workerEnqueueTest() {
        val request = OneTimeWorkRequestBuilder<RefreshCacheWorker>()
            .build()
        workManager.enqueue(request).result.get()
        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertThat(workInfo.state, not(WorkInfo.State.FAILED),)
    }

}
package com.test.rxjavasample.operator.combine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.rxjavasample.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CombinLatestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combin_latest)

        val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
        val observable2 = Observable.interval(50, TimeUnit.MILLISECONDS)

        Observable.combineLatest(observable1, observable2,
            { observable1Times: Long, observable2Times: Long -> "Refreshed Observable1: $observable1Times refreshed Observable2: $observable2Times" })
            .subscribe { item: String? -> println(item) }

        Thread.sleep(1000)
    }
}
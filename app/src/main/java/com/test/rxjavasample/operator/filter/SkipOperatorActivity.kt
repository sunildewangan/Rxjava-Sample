package com.test.rxjavasample.operator.filter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit

//skip(n) operator suppresses the first n items emitted by an Observable.
class SkipOperatorActivity : AppCompatActivity() {
    private val TAG = "SkipOperatorActivity"
    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skip_operator)

        // Example 1
        disposable.add(
            Observable.just(1, 2, 3, 4, 5, 6)
                .skip(3)
                .subscribeWith(object : DisposableObserver<Int?>() {
                    override fun onNext(integer: Int) {
                        Log.d(TAG, "onNext: $integer")
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {
                        Log.d(TAG, "All items emitted!")
                    }
                })
        )


        val observable1 = Observable.interval(100, TimeUnit.MILLISECONDS)
        val observable2 = Observable.interval(50, TimeUnit.MILLISECONDS)

        Observable.combineLatest(observable1, observable2,
            { observable1Times: Long, observable2Times: Long -> "Refreshed Observable1: $observable1Times refreshed Observable2: $observable2Times" })
            .subscribe { item: String? -> println(item) }

        Thread.sleep(1000)

    }
}
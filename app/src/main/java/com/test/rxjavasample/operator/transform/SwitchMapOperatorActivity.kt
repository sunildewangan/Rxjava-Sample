package com.test.rxjavasample.operator.transform

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


//it returns the latest Observable and emits the items from it.
class SwitchMapOperatorActivity : AppCompatActivity() {
    private val TAG = "SwitchMapOperatorActivi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_map_operator)

        val integerObservable = Observable.fromArray(*arrayOf(1, 2, 3, 4, 5, 6))

        // it always emits 6 as it un-subscribes the before observer
        integerObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap { integer ->
                Observable.just(integer).delay(1, TimeUnit.SECONDS)
            }
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe")
                }

                override fun onNext(integer: Int) {
                    Log.d(TAG, "onNext: $integer")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.d(TAG, "All users emitted!")
                }
            })
    }
}
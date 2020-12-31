package com.test.rxjavasample.operator.create

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

private const val TAG = "JustOperatorActivity"
class JustOperatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_just_operator)


        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(integer: Int) {
                    Log.d(TAG, "just onNext: $integer")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.d(TAG, "just onComplete")
                }
            })
    }
}
package com.test.rxjavasample.operator.create

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

private const val TAG = "RepeatOperatorActivity"
class RepeatOperatorActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat_operator)


        Observable
            .fromArray(*arrayOf(10,20,30,40,50,60,70,80,90,100))
            .repeat(2)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    Log.d(TAG, "onSubscribe: ")
                }

                override fun onNext(t: Int) {
                    Log.d(TAG, "onNext: $t")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                }

            })



    }
}
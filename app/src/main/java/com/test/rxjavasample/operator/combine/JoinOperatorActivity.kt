package com.test.rxjavasample.operator.combine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class JoinOperatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_operator)

        val left: Observable<Long> = Observable
            .interval(100, TimeUnit.MILLISECONDS)

        val right: Observable<Long> = Observable
            .interval(100, TimeUnit.MILLISECONDS)

        left.join(right,
            { aLong -> Observable.timer(0, TimeUnit.SECONDS) },
            { aLong -> Observable.timer(0, TimeUnit.SECONDS) }
        ) { l, r ->
            println("Left result: $l Right Result: $r")
            l + r
        }
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(aLong: Long) {
                    println("onNext: $aLong")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })

        Thread.sleep(1000)
    }
}
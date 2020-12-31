package com.test.rxjavasample.operator.transform

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observables.GroupedObservable


class GroupByOperatorActivity : AppCompatActivity() {
    private val TAG = "GroupByOperatorActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_by_operator)

        Observable.range(1, 10)
            .groupBy { p0:Int -> (p0%2==0) }
            .subscribe(object : Observer<GroupedObservable<Boolean, Int>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(booleanIntegerGroupedObservable: GroupedObservable<Boolean, Int>) {
                    if (booleanIntegerGroupedObservable.key!!) {
                        booleanIntegerGroupedObservable.subscribe(object : Observer<Int> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onNext(integer: Int) {
                                println("onNext: $integer")
                            }

                            override fun onError(e: Throwable) {}
                            override fun onComplete() {}
                        })
                    }
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: ")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete:")
                }

            })
    }
}
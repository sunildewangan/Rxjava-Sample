package com.test.rxjavasample.operator.create

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable



/*This operator creates an Observable that emits a range of sequential integers.
The function takes two arguments: the starting number and length.*/

private const val TAG = "RangeOperatorActivity"
class RangeOperatorActivity : AppCompatActivity() {
    private var disposable: Disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_range_operator)

        // range(start, count)
        // range(1, 10) - emits values from 1,2,3 ..., 9,10
        // range(10, 10) - emits values from 10, 11, ..., 18,19
        Observable.range(10, 10)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(integer: Int) {
                    Log.d(TAG, "onNext: $integer")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
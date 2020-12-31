package com.test.rxjavasample.operator.create

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers



/*This operator creates an Observable from set of items using an Iterable,
        which means we can pass a list or an array of items to the Observable and
        each item is emitted one at a time. Some of the examples of the operators
        include fromCallable(), fromFuture(), fromIterable(), fromPublisher(), fromArray().*/
class FromOperatorActivity : AppCompatActivity() {
    private val TAG = "FromOperatorActivity"
    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_operator)

        val numbers = arrayOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20
        )


        disposable.add(
            Observable.fromArray(*numbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onNext(integer: Int) {
                        Log.e(TAG, "Number: $integer")
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {
                        Log.e(TAG, "All numbers emitted!")
                    }
                })
        )




        Observable.fromArray(*numbers)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<Int?>() {
                override fun onNext(integer: Int) {
                    Log.e(TAG, "Number: $integer")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.e(TAG, "All numbers emitted!")
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
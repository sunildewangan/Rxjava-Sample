package com.test.rxjavasample.operator.transform

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//This operator periodically gather items from an Observable into bundles and
//emit these bundles rather than emitting the items one at a time.
private const val TAG = "BufferOperatorActivity"
class BufferOperatorActivity : AppCompatActivity() {
    lateinit var txtTapResult: TextView
    lateinit var txtTapResultMax: TextView
    lateinit var btnTapArea: Button
    lateinit var disposable: Disposable
    private var maxTaps = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buffer_operator)


        initView()

        /**
         * Example of buffer emitting 3 items at a time
         */
        val integerObservable = Observable.just(
            1, 2, 3, 4,
            5, 6, 7, 8, 9
        )

        integerObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(3)
            .subscribe(object : Observer<List<Int>> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(integers: List<Int>) {
                    Log.d(TAG, "onNext")
                    for (integer in integers) {
                        Log.d(TAG, "Item: $integer")
                    }
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.d(TAG, "All items emitted!")
                }
            })
    }

    private fun initView(){
        txtTapResult = findViewById(R.id.tap_result)
        txtTapResultMax = findViewById(R.id.tap_result_max_count)
        btnTapArea = findViewById(R.id.layout_tap_area)

        btnTapArea.setOnClickListener(View.OnClickListener {


            val integerObservable = Observable.just(
                1, 2, 3, 4,
                5, 6, 7, 8, 9
            )

            integerObservable
                .buffer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : Observer<List<Int?>> {
                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(integers: List<Int?>) {
                        Log.e(
                            TAG,
                            "onNext: " + integers.size + " taps received!"
                        )
                        if (integers.size > 0) {
                            maxTaps = if (integers.size > maxTaps) integers.size else maxTaps
                            txtTapResult.text =
                                String.format("Received %d taps in 3 secs", integers.size)
                            txtTapResultMax.text =
                                String.format(
                                    "Maximum of %d taps received in this session",
                                    maxTaps
                                )
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e.message)
                    }

                    override fun onComplete() {
                        Log.e(TAG, "onComplete")
                    }
                })

        })
    }
}
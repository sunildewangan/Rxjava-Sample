package com.test.rxjavasample.operator.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

//his operator creates an Observable from scratch by calling observer methods programmatically
class CreateOperatorActivity : AppCompatActivity() {
    val alphabets: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_operator)


    }
}
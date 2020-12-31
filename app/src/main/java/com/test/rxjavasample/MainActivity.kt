package com.test.rxjavasample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.rxjavasample.operator.combine.CombinLatestActivity
import com.test.rxjavasample.operator.combine.ConcatOperatorActivity
import com.test.rxjavasample.operator.combine.JoinOperatorActivity
import com.test.rxjavasample.operator.combine.MergeOperatorActivity
import com.test.rxjavasample.operator.filter.DebounceOperatorActivity
import com.test.rxjavasample.operator.filter.DistinctOperatorActivity
import com.test.rxjavasample.operator.filter.FilterOperatorActivity
import com.test.rxjavasample.operator.transform.GroupByOperatorActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, ConcatOperatorActivity::class.java))
    }
}
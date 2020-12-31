package com.test.rxjavasample.operator.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


/*This operator only emits an item from an Observable
if a particular timespan has passed without it emitting another item.*/
class DebounceOperatorActivity : AppCompatActivity() {
    private val TAG = "DebounceOperatorActivity"
    private val disposable = CompositeDisposable()
    lateinit var inputSearch:SearchView
    lateinit var txtSearchString:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debounce_operator)

        initView()
    }

    private fun initView(){
        inputSearch = findViewById(R.id.input_search)
        txtSearchString = findViewById(R.id.txt_search_string)

        RxSearchObservable.fromView(inputSearch)
            .debounce(300,TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(@SuppressLint("CheckResult")
            object :Consumer<String>{
                override fun accept(t: String?) {
                    txtSearchString.text = t
                }

            })

    }


    object RxSearchObservable {
        fun fromView(searchView: SearchView): Observable<String> {
            val subject = PublishSubject.create<String>()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String?): Boolean {
                    subject.onComplete()
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    subject.onNext(text)
                    return true
                }
            })
            return subject
        }
    }
}


package com.test.rxjavasample.operator.filter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import com.test.rxjavasample.observers.model.Note
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

//This operator suppresses duplicate items emitted by an Observable
//to work with a custom dataType, we need to override the equals() and hashCode() methods.
class DistinctOperatorActivity : AppCompatActivity() {
    private val TAG = "DistinctOperatorActivit"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distinct_operator)

        //Example1 - Primitive value
        val numbersObservable = Observable.just(10, 10, 15, 20, 100, 200, 100, 300, 20, 100)

        numbersObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .distinct()
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(integer: Int) {
                    Log.d(TAG, "onNext: $integer")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })

        //Example2 - Custom data

        // Example 2
        val notesObservable: Observable<Note> = getNotesObservable()

        val notesObserver: DisposableObserver<Note> = getNotesObserver()

        notesObservable.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .distinct()
            .subscribeWith<DisposableObserver<Note>>(notesObserver)

    }

    private fun getNotesObserver(): DisposableObserver<Note> {
        return object : DisposableObserver<Note>() {
            override fun onNext(note: Note) {
                Log.e(TAG, "onNext: " + note.note)
            }

            override fun onError(e: Throwable) {}
            override fun onComplete() {
                Log.e(TAG, "onComplete")
            }
        }
    }

    private fun getNotesObservable(): Observable<Note> {
        val notes: List<Note> = prepareNotes()
        return Observable.create { emitter ->
            for (note in notes) {
                if (!emitter.isDisposed) {
                    emitter.onNext(note)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }
    }

    private fun prepareNotes(): List<Note> {
        val notes: MutableList<Note> = ArrayList()
        notes.add(Note(1, "Buy tooth paste!"))
        notes.add(Note(2, "Call brother!"))
        notes.add(Note(3, "Call brother!"))
        notes.add(Note(4, "Pay power bill!"))
        notes.add(Note(5, "Watch Narcos tonight!"))
        notes.add(Note(6, "Buy tooth paste!"))
        notes.add(Note(7, "Pay power bill!"))
        return notes
    }
}
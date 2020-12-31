package com.test.rxjavasample.operator.filter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import com.test.rxjavasample.operator.model.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

/*This operator emits only those items from an Observable that pass a predicate test.*/

class FilterOperatorActivity : AppCompatActivity() {
    private val TAG = "FilterOperatorActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_operator)

        // example1
        Observable
            .just(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .filter { integer -> integer % 2 == 0 }
            .subscribe(object : DisposableObserver<Int?>() {
                override fun onNext(integer: Int) {
                    Log.e(TAG, "Even: $integer")
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })


        //Example2
        val userObservable: Observable<User> = getUsersObservable()

        userObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter(Predicate { user -> user.gender.equals("female") })
            .subscribeWith(object : DisposableObserver<User?>() {
                override fun onNext(user: User) {
                    Log.e(TAG, user.name.toString() + ", " + user.gender)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    private fun getUsersObservable(): Observable<User> {
        val maleUsers = arrayOf("Mark", "John", "Trump", "Obama")
        val femaleUsers = arrayOf("Lucy", "Scarlett", "April")
        val users: MutableList<User> = ArrayList()
        for (name in maleUsers) {
            val user = User()
            user.name = name
            user.gender = "male"
            users.add(user)
        }
        for (name in femaleUsers) {
            val user = User()
            user.name = name
            user.gender = "female"
            users.add(user)
        }
        return Observable
            .create(ObservableOnSubscribe<User> { emitter ->
                for (user in users) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(user)
                    }
                }
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }).subscribeOn(Schedulers.io())
    }
}
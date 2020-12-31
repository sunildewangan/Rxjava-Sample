package com.test.rxjavasample.operator.combine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import com.test.rxjavasample.operator.model.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/*the first Observables completes its emission before the second starts and so forth if there are more observables.*/
class ConcatOperatorActivity : AppCompatActivity() {
    private val TAG = "ConcatOperatorActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concat_operator)

        /**
         * When Concat is used, item emission maintains sequential order
         * You can see Male users printed first then Female users
         */
        Observable
            .concat(getMaleObservable(), getFemaleObservable())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(user: User) {
                    Log.e(TAG, user.name.toString() + ", " + user.gender)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })

    }

    private fun getFemaleObservable(): Observable<User> {
        val names = arrayOf("Lucy", "Scarlett", "April")
        val users: MutableList<User> = ArrayList<User>()
        for (name in names) {
            val user = User()
            user.setName(name)
            user.setGender("female")
            users.add(user)
        }
        return Observable
            .create(ObservableOnSubscribe<User> { emitter ->
                for (user in users) {
                    if (!emitter.isDisposed) {
                        Thread.sleep(1000)
                        emitter.onNext(user)
                    }
                }
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }).subscribeOn(Schedulers.io())
    }

    private fun getMaleObservable(): Observable<User> {
        val names = arrayOf("Mark", "John", "Trump", "Obama")
        val users: MutableList<User> = ArrayList<User>()
        for (name in names) {
            val user = User()
            user.setName(name)
            user.setGender("male")
            users.add(user)
        }
        return Observable
            .create(ObservableOnSubscribe<User> { emitter ->
                for (user in users) {
                    if (!emitter.isDisposed) {
                        Thread.sleep(500)
                        emitter.onNext(user)
                    }
                }
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }).subscribeOn(Schedulers.io())
    }
}
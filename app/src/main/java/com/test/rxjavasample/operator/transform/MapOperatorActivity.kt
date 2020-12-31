package com.test.rxjavasample.operator.transform

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


//map() operator allows for us to modify the emitted item from the Observable
//and then emits the modified item.
//Notice that the order of insertion is maintained during emission.
class MapOperatorActivity : AppCompatActivity() {
    private val TAG = "MapOperatorActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_operator)


        getUsersObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { user ->
                user.email = String.format("%s@gmail.com", user.name)
                user.name = user.name.toUpperCase()
                user
            }
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                }

                override fun onNext(user: User) {
                    Log.e(TAG,
                        "onNext: " + user.name.toString() + ", " + user.gender.toString() + ", " + user.email
                    )
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

                override fun onComplete() {
                    Log.e(TAG, "All users emitted!")
                }
            })
    }



        /**
         * Assume this method is making a network call and fetching Users
         * an Observable that emits list of users
         * each User has name and email, but missing email id
         */
         fun getUsersObservable(): Observable<User> {
            val names = arrayOf("mark", "john", "trump", "obama")
            val users: MutableList<User> = ArrayList<User>()
            for (name in names) {
                val user = User()
                user.name = name
                user.gender = "male"
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

package com.test.rxjavasample.operator.transform

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import com.test.rxjavasample.operator.model.Address
import com.test.rxjavasample.operator.model.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*

//This operator functions the same way as flatMap(),
//the difference being in concatMap() the order in which items are emitted are maintained

class ConcatMapOperatorActivity : AppCompatActivity() {
    private  val TAG = "ConcatMapOperatorActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concat_map_operator)




        /**
         * Assume this as a network call
         * returns Users with address filed added
         */
         fun getAddressObservable(user: User): Observable<User> {
            val addresses = arrayOf(
                "1600 Amphitheatre Parkway, Mountain View, CA 94043",
                "2300 Traverwood Dr. Ann Arbor, MI 48105",
                "500 W 2nd St Suite 2900 Austin, TX 78701",
                "355 Main Street Cambridge, MA 02142"
            )
            return Observable
                .create(ObservableOnSubscribe<User> { emitter ->
                    val address = Address()
                    address.setAddress(addresses[Random().nextInt(2) + 0])
                    if (!emitter.isDisposed) {
                        user.setAddress(address)


                        // Generate network latency of random duration
                        val sleepTime = Random().nextInt(1000) + 500
                        Thread.sleep(sleepTime.toLong())
                        emitter.onNext(user)
                        emitter.onComplete()
                    }
                }).subscribeOn(Schedulers.io())
        }

        /**
         * Assume this is a network call to fetch users
         * returns Users with name and gender but missing address
         */
         fun getUsersObservable(): Observable<User> {
            val maleUsers = arrayOf("Mark", "John", "Trump", "Obama")
            val users: MutableList<User> = ArrayList<User>()
            for (name in maleUsers) {
                val user = User()
                user.setName(name)
                user.setGender("male")
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

        getUsersObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap { t -> getAddressObservable(t) }
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")

                }

                override fun onNext(user: User) {
                    Log.e(
                        TAG, "onNext: " + user.name.toString() + ", " + user.gender.toString() + ", " + user.address.address
                    )
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                    Log.e(TAG, "All users emitted!")
                }
            })

    }
}
package com.test.rxjavasample.operator.combine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.rxjavasample.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class ZipOperatorActivity : AppCompatActivity() {
    private val TAG = "ZipOperatorActivity"
     var disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zip_operator)

        disposable.add(
            Observable.zip(
                getAddressObservable(), getPhotosObservable(),
                BiFunction<Address, Photo, User> { address, photo ->
                    val user: User = User(address,photo)
                    user
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<User>() {
                    override fun onNext(user: User) {
                        Log.d(TAG,
                            "onNext- Address: " + user.address.address + ", Photo: " +
                                    user.photo.url + ", " + user.photo
                                .width + "x" + user.photo.height
                        )
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {
                        Log.d(TAG, "All users are emitted!")
                    }
                })
        )
    }

    private fun getPhotosObservable(): Observable<Photo> {
        val photos: MutableList<Photo> =
            ArrayList<Photo>()
        for (i in 0..3) {
            val photo: Photo = Photo("https://example.com/photo/$i.jpg", 100, 150)
            photos.add(photo)
        }
        return Observable.create(ObservableOnSubscribe<Photo> { emitter ->
            for (photo in photos) {
                if (!emitter.isDisposed) {
                    emitter.onNext(photo)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        })
    }

    private fun getAddressObservable(): Observable<Address> {
        val addreStrns = arrayOf(
            "1600 Amphitheatre Parkway, Mountain View, CA 94043",
            "2300 Traverwood Dr. Ann Arbor, MI 48105",
            "500 W 2nd St Suite 2900 Austin, TX 78701",
            "355 Main Street Cambridge, MA 02142"
        )
        val addresses: MutableList<Address> =
            ArrayList<Address>()
        for (a in addreStrns) {
            val address: Address = Address(a)
            addresses.add(address)
        }
        return Observable.create(ObservableOnSubscribe<Address> { emitter ->
            for (address in addresses) {
                if (!emitter.isDisposed) {
                    emitter.onNext(address)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        })
    }

    data class User(val address: Address, val photo: Photo)

    data class Address(val address: String)

    class Photo(val url: String, val width: Int, val height: Int)
}
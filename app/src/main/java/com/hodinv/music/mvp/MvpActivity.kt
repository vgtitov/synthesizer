package com.hodinv.music.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by vasily on 25.05.17.
 */
abstract class MvpActivity<V : MvpView, R : MvpRouter, P : MvpPresenter<V, R>> : AppCompatActivity() {

    var presenter: P? = null

    abstract fun createPresenter(): P
    abstract fun getView(): V
    abstract fun getRouter(): R


    fun initOrRestorePresenter() {
        @Suppress("UNCHECKED_CAST")
        presenter = MvpActivity.getPresenter(this.localClassName) as P?
        if (presenter == null) {
            presenter = createPresenter()
            MvpActivity.savePresenter(this.localClassName, presenter!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOrRestorePresenter()
    }


    override fun onStart() {
        super.onStart()
        presenter?.view = getView()
        presenter?.router = getRouter()
        presenter?.setOrResetView()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbindListeners()
        presenter?.view = null
        presenter?.router = null
    }

    override fun finish() {
        super.finish()
        dismissPresenter(this.localClassName)
        presenter?.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dismissPresenter(this.localClassName)
        presenter?.dismiss()
    }

    companion object {
        var presenters = HashMap<String, MvpPresenter<*, *>>()

        fun getPresenter(classKey: String): MvpPresenter<*, *>? {
            return presenters[classKey]
        }

        fun savePresenter(localClassName: String, presenter: MvpPresenter<*, *>) {
            presenters.put(localClassName, presenter)
        }

        fun dismissPresenter(classKey: String) {
            presenters.remove(classKey)
        }
    }
}




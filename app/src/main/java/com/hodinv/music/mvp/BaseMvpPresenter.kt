package com.hodinv.music.mvp

/**
 * Created by vasily on 25.05.17.
 */
abstract class BaseMvpPresenter<V : MvpView, R : MvpRouter> : MvpPresenter<V, R> {
    override var view: V? = null
    override var router: R? = null

    override fun setOrResetView() {

    }

    override fun unbindListeners() {

    }

    override fun dismiss() {
        // do nothing by default
    }
}

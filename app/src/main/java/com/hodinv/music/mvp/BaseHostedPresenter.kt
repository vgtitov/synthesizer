package com.hodinv.music.mvp

/**
 * Created by vasily on 25.08.17.
 */
abstract class BaseHostedPresenter<V : MvpView, T> : MvpHostedPresenter<V, T> {
    override var view: V? = null
    override var hostPresenter: T? = null

    override fun setupUi() {

    }

}
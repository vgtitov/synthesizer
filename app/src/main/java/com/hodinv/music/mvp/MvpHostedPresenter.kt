package com.hodinv.music.mvp

/**
 * Created by vasily on 24.08.17.
 */
interface MvpHostedPresenter<V : MvpView, T> {
    var view: V?
    var hostPresenter: T?
    fun setupUi()
}
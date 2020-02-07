package com.hodinv.music.mvp

/**
 * Created by vasily on 21.05.17.
 */
interface MvpPresenter<V : MvpView, R : MvpRouter> {

    var view: V?
    var router: R?

    /**
     * Set view state according to model values
     */
    fun setOrResetView()
    fun unbindListeners()
    fun dismiss()
}


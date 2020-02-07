package com.hodinv.music.mvp

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by vasily on 24.08.17.
 */

abstract class MvpDialog<V : MvpView, T, P : MvpHostedPresenter<V, T>> : DialogFragment() {

    var presenter: P? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectUi(view)
        presenter = createPresenter()
        presenter?.hostPresenter = MvpActivity.getPresenter(activity.localClassName) as T
        presenter?.view = getMvpView()
        presenter?.setupUi()
    }

    abstract fun createPresenter(): P
    abstract fun getMvpView(): V
    abstract fun getLayoutId() : Int
    abstract fun connectUi(view: View?)
}

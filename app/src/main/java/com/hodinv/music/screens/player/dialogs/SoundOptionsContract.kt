package com.hodinv.music.screens.player.dialogs

import com.hodinv.music.model.BassMode
import com.hodinv.music.mvp.MvpHostedPresenter
import com.hodinv.music.mvp.MvpView

/**
 * Created by vasily on 25.08.17.
 */
interface SoundOptionsContract {
    interface View : MvpView {
        fun setTempo(tempo: Int)
        fun setSizePos(pos: Int)
        fun setBassPos(pos: Int)
    }

    interface HostPresenter {
        fun setBassMode(bassMode: BassMode)
        fun setTempo(tempo: Int)
        fun setTempoSize(size: Int)
        fun getBassMode(): BassMode
        fun getTempo(): Int
        fun getTempoSize(): Int

    }

    interface Presenter : MvpHostedPresenter<SoundOptionsContract.View, SoundOptionsContract.HostPresenter> {
        fun confirm(tempo: Int?, size: Int?, bass: Int?)
    }
}
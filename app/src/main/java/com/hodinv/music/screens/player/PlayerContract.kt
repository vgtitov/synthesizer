package com.hodinv.music.screens.player

import android.content.Context
import com.hodinv.music.model.BassMode
import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone
import com.hodinv.music.mvp.MvpPresenter
import com.hodinv.music.mvp.MvpRouter
import com.hodinv.music.mvp.MvpView
import com.hodinv.music.screens.player.dialogs.SoundOptionsContract

/**
 * Created by vasily on 05.07.17.
 */
interface PlayerContract {
    interface PlayerView : MvpView {
        fun blinkButton(note: Note, tone: Tone)
        fun blinkNoButtons()
        fun getContext(): Context
        fun setCurrentBassMode(bassMode: BassMode)
        fun setLabelRotation(angle: Int)
        fun getBaseOrientation(): Int
        fun setCurrentTempo(tempo: Int)

    }

    interface PlayerRouter : MvpRouter
    interface PlayerPresenter : MvpPresenter<PlayerView, PlayerRouter>, SoundOptionsContract.HostPresenter {
        override fun setBassMode(bassMode: BassMode)
        override fun setTempo(tempo: Int)
        fun orientationChanged(newOrientation: Int)
        fun buttonPressed(note: Note, tone: Tone)
        fun stopMusic()
    }
}
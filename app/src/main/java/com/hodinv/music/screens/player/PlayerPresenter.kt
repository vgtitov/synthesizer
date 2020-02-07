package com.hodinv.music.screens.player

import android.content.pm.ActivityInfo
import android.view.Surface
import com.hodinv.music.model.BassMode
import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone
import com.hodinv.music.mvp.BaseMvpPresenter
import com.hodinv.music.repository.PlayerSettingsRepository
import com.hodinv.music.services.BassProducer
import com.hodinv.music.services.BassProducerContract
import com.hodinv.music.services.MusicServiceContract
import java.util.*

/**
 * Created by vasily on 05.07.17.
 */
class PlayerPresenter(val musicService: () -> MusicServiceContract, val settings: PlayerSettingsRepository) : BaseMvpPresenter<PlayerContract.PlayerView, PlayerContract.PlayerRouter>(), PlayerContract.PlayerPresenter {
    override fun setTempoSize(size: Int) {
        currentBassSize = size
        resetBass()
    }

    override fun getBassMode(): BassMode {
        return currentBassMode
    }

    override fun getTempo(): Int {
        return currentBassTempo
    }

    override fun getTempoSize(): Int {
        return currentBassSize
    }

    override fun setTempo(tempo: Int) {
        currentBassTempo = tempo
        resetBass()
    }

    override fun setBassMode(bassMode: BassMode) {
        currentBassMode = bassMode
        resetBass()
    }

    override fun orientationChanged(newOrientation: Int) {
        var rot = 0
        if (view?.getBaseOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            when (newOrientation) {
                Surface.ROTATION_0 -> rot = 0
                Surface.ROTATION_90 -> rot = 90
                Surface.ROTATION_180 -> rot = 180
                Surface.ROTATION_270 -> rot = 270
            }
        } else {
            when (newOrientation) {
                Surface.ROTATION_0 -> rot = 270
                Surface.ROTATION_90 -> rot = 0
                Surface.ROTATION_180 -> rot = 90
                Surface.ROTATION_270 -> rot = 180
            }
        }
        view?.setLabelRotation(rot)
    }


    override fun stopMusic() {
        service?.stopAll()
        lastNote = null
        lastTone = null
        view?.blinkNoButtons()
    }

    private var lastNote: Note? = null
    private var lastTone: Tone? = null
    private var currentBassMode: BassMode = settings.mode
        set(value) {
            field = value
            settings.mode = value
            settings.save()
        }

    private var currentBassSize: Int = settings.size
        set(value) {
            field = value
            settings.size = value
            settings.save()
        }

    private var currentBassTempo: Int = settings.tempo
        set(value) {
            field = value
            settings.tempo = value
            settings.save()
        }

    var service: MusicServiceContract? = null
    var bassProducer: BassProducerContract? = null
    var lastNoteTime = 0L


    override fun setOrResetView() {
        super.setOrResetView()
        view?.blinkNoButtons()
        view?.setCurrentBassMode(currentBassMode)
        view?.setCurrentTempo(currentBassTempo)
        if (view != null) {
            service?.stopAll()
            service = musicService()
            resetBass()
        }
    }

    private fun resetBass() {
        if (service == null) return
        bassProducer?.stopBass()
        bassProducer = BassProducer(service!!)
        bassProducer?.mode = currentBassMode
        bassProducer?.tempo = currentBassTempo / currentBassSize
    }

    override fun unbindListeners() {
        super.unbindListeners()
        service?.stopAll()
        lastNote = null
        lastTone = null
    }

    override fun dismiss() {
        super.dismiss()
        lastNote = null
        lastTone = null
    }

    override fun buttonPressed(note: Note, tone: Tone) {
        if (note != lastNote || tone != lastTone || Date().time - lastNoteTime > CORD_RESTART_LENGTH) {
            //service?.playBass(note)
            bassProducer?.playBass(note, tone)
            view?.blinkButton(note, tone)
            service?.playCord(note, tone)
            lastNoteTime = Date().time
        } else {
            //service?.playBass(note)
            bassProducer?.playBass(note, tone)
        }
        lastNote = note
        lastTone = tone
    }

    companion object {
        val CORD_RESTART_LENGTH = 2 * 1000L
    }
}
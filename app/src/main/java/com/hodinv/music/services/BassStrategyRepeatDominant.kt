package com.hodinv.music.services

import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone
import java.util.*

/**
 * Created by vasily on 30.07.17.
 */
class BassStrategyRepeatDominant(service: MusicServiceContract, currentTempo: Int) : BassStrategyRepeat(service, currentTempo) {

    var playMain = true

    inner class MyTimerTick : TimerTask() {
        override fun run() {
            val currTime = Date().time
            if (currTime - clickTime < BASS_REPEAT_LEMGTH) {
                if (currentNote != null) {
                    playedTime = currTime
                    service.playBass(if (playMain) currentNote!! else currentNote!!.getDominant())
                    playMain = !playMain
                }
            } else {
                stopped = true
            }
        }
    }


    override fun createTimer(): TimerTask {
        return MyTimerTick()
    }

    override fun play(note: Note, tone: Tone) {
        playMain = true
        super.play(note, tone)
    }
}
package com.hodinv.music.services

import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone

/**
 * Created by vasily on 30.07.17.
 */
class BassStrategySingle(val service: MusicServiceContract) : BassStrategy {
    override fun stop() {

    }

    override fun setTempo(value: Int) {

    }

    override fun play(note: Note, tone: Tone) {
        service.playBass(note)
    }
}
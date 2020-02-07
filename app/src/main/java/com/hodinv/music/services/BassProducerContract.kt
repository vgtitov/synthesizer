package com.hodinv.music.services

import com.hodinv.music.model.BassMode
import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone

/**
 * Created by vasily on 30.07.17.
 */
interface BassProducerContract {
    fun stopBass()
    fun playBass(note: Note, tone: Tone)
    var mode: BassMode
    var tempo: Int
}
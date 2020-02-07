package com.hodinv.music.services

import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone

/**
 * Created by vasily on 30.07.17.
 */
interface BassStrategy {
    fun stop()
    fun setTempo(value: Int)
    fun play(note: Note, tone: Tone)
}
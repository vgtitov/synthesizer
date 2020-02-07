package com.hodinv.music.services

import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone

/**
 * Created by vasily on 26.07.17.
 */
interface MusicServiceContract {
    fun stopAll()
    fun playBass(note: Note)
    fun playCord(note: Note, tone: Tone)
}
package com.hodinv.music.services

import com.hodinv.music.model.BassMode
import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone

/**
 * Created by vasily on 30.07.17.
 */
class BassProducer(val service: MusicServiceContract) : BassProducerContract {
    override var mode: BassMode = BassMode.SINGLE
        set(value) {
            strategy = when (value) {
                BassMode.NONE -> BassStrategyNone()
                BassMode.SINGLE -> BassStrategySingle(service)
                BassMode.REPEATE_TONE -> BassStrategyRepeat(service, tempo)
                BassMode.REPTEATE_TONE_DOMINANTE -> BassStrategyRepeatDominant(service, tempo)
            }
        }
    override var tempo: Int = 120
        set(value) {
            strategy.setTempo(value)
        }

    var strategy: BassStrategy = BassStrategySingle(service)
    override fun stopBass() {
        strategy.stop()
    }

    override fun playBass(note: Note, tone: Tone) {
        strategy.play(note, tone)
    }
}
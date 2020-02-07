package com.hodinv.music.services

import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone
import java.util.*

/**
 * Created by vasily on 30.07.17.
 */
open class BassStrategyRepeat(var service: MusicServiceContract, var currentTempo: Int) : BassStrategy {


    var timer: Timer? = null
    var clickTime: Long = 0L
    var stopped = true
    var playedTime = 0L

    inner class MyTimerTick : TimerTask() {
        override fun run() {
            val currTime = Date().time
            if (currTime - clickTime < BASS_REPEAT_LEMGTH) {
                if (currentNote != null) {
                    playedTime = currTime
                    service.playBass(currentNote!!)
                }
            } else {
                stopped = true
            }
        }
    }


    open fun createTimer():TimerTask {
        return MyTimerTick()
    }


    init {
        if (currentTempo <= 0) {
            currentTempo = 1
        }
    }

    private var rate: Long = 1L

    private fun startTimer() {
        timer?.cancel()
        timer = Timer()
        rate = 1000L * 60 / currentTempo
        timer?.scheduleAtFixedRate(createTimer(), 0L, rate)
        stopped = false
    }

    override fun stop() {
        timer?.cancel()
        timer = null
    }

    override fun setTempo(value: Int) {
        if (currentTempo > 0) {
            currentTempo = value
        }
    }

    protected var currentNote: Note? = null
    protected var currentTone: Tone? = null

    override fun play(note: Note, tone: Tone) {
        val currTime = Date().time
        val forceRestart = checkRestartRules(note, tone, currTime)
        currentNote = note
        currentTone = tone
        clickTime = currTime
        if (stopped || forceRestart) {
            startTimer()
        }
    }

    /**
     * Strategy of forced restart
     * B - Bass of prev accord
     * B'-Second bass (where it should be)
     * b - bass of new accord
     * P - pressed button
     * Prev accord
     * |B--------------|B'--------------|
     * New accord
     * .............P..|b---------------|b---               same tempo,
     *           ^ - threshold 1                            just new bass when time is comming
     *
     *
     * .....Pb---------------|b---                          immediate restart
     *           ^ - threshold 1
     *
     *
     * ................|B.P-------------|b---               accorder restarted,
     *                       ^ - threshold 2                bass will be played next time
     *                                                      (no double bass)
     *
     * ................|B.......Pb---------------|b---      immediate restart
     *                       ^ - threshold 2
     *
     * | + + + + + + + + * * * * * * * * * * + + + + + + + + + |
     * ^ - playedTime   ^ - threshold 1     ^ - threshold 2    ^ - playedTime + rate
     * + - not restrting
     * * - restarting
     *
     *
     */
    private fun checkRestartRules(note: Note, tone: Tone, currTime: Long): Boolean {
        if (currentNote == note) return false
        return currTime > playedTime + rate * THRESHOLD1 && currTime < playedTime + rate * THRESHOLD2
    }

    companion object {
        val BASS_REPEAT_LEMGTH = 5 * 1000L
        val THRESHOLD1 = 0.2f
        val THRESHOLD2 = 0.8f
    }
}
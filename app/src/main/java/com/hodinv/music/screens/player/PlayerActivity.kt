package com.hodinv.music.screens.player

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import com.hodinv.music.R
import com.hodinv.music.model.BassMode
import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone
import com.hodinv.music.mvp.MvpActivity
import com.hodinv.music.repository.PlayerSettingsRepository
import com.hodinv.music.screens.player.dialogs.SoundOptionsDialog
import com.hodinv.music.services.MusicService
import com.pawegio.kandroid.loadAnimation
import com.pawegio.kandroid.sensorManager
import com.pawegio.kandroid.views


class PlayerActivity : MvpActivity<PlayerContract.PlayerView, PlayerContract.PlayerRouter, PlayerContract.PlayerPresenter>(), PlayerContract.PlayerView, PlayerContract.PlayerRouter {
    override fun setCurrentTempo(tempo: Int) {
        // do nothing for now
    }

    override fun setCurrentBassMode(bassMode: BassMode) {
        // do nothing for now
    }

    override fun setLabelRotation(angle: Int) {
        val layout = findViewById<LinearLayout>(R.id.lyt_labels)
        layout.views.forEach { it.rotation = angle.toFloat() }
    }

    override fun getBaseOrientation(): Int {
        return requestedOrientation
    }

    override fun getContext(): Context {
        return this
    }

    var animationButton: Button? = null

    override fun blinkButton(note: Note, tone: Tone) {
        val button: Button? = getButton(tone, note)
        animationButton?.clearAnimation()
        if (button != null) {
            val animation = loadAnimation(R.anim.blink)
            button.startAnimation(animation)
        }
        animationButton = button
    }

    private fun getButton(tone: Tone, note: Note): Button? {
        return when (tone) {
            Tone.DUR -> when (note) {
                Note.NOTE_C -> findViewById<Button>(R.id.btn_c_dur)
                Note.NOTE_D -> findViewById<Button>(R.id.btn_d_dur)
                Note.NOTE_E -> findViewById<Button>(R.id.btn_e_dur)
                Note.NOTE_F -> findViewById<Button>(R.id.btn_f_dur)
                Note.NOTE_G -> findViewById<Button>(R.id.btn_g_dur)
                Note.NOTE_A -> findViewById<Button>(R.id.btn_a_dur)
                Note.NOTE_H -> findViewById<Button>(R.id.btn_h_dur)
                else -> null
            }
            Tone.MOLL -> when (note) {
                Note.NOTE_C -> findViewById<Button>(R.id.btn_c_moll)
                Note.NOTE_D -> findViewById<Button>(R.id.btn_d_moll)
                Note.NOTE_E -> findViewById<Button>(R.id.btn_e_moll)
                Note.NOTE_F -> findViewById<Button>(R.id.btn_f_moll)
                Note.NOTE_G -> findViewById<Button>(R.id.btn_g_moll)
                Note.NOTE_A -> findViewById<Button>(R.id.btn_a_moll)
                Note.NOTE_H -> findViewById<Button>(R.id.btn_h_moll)
                else -> null
            }
        }
    }

    override fun blinkNoButtons() {
        for (tone in Tone.values()) {
            for (note in Note.values()) {
                getButton(tone, note)?.clearAnimation()
            }
        }
    }

    override fun createPresenter(): PlayerContract.PlayerPresenter {
        return PlayerPresenter({ MusicService(this) }, PlayerSettingsRepository(this))
    }

    override fun getView(): PlayerContract.PlayerView {
        return this
    }

    override fun getRouter(): PlayerContract.PlayerRouter {
        return this
    }

    private val orientationSensorListener = SensorLogic({
        presenter?.orientationChanged(it)
    })

    private var sensorOrientation: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupButtons()
        sensorOrientation = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    }

    private fun setupButtons() {

        findViewById<Button>(R.id.btn_c_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_C, Tone.DUR) }
        findViewById<Button>(R.id.btn_c_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_C, Tone.MOLL) }

        findViewById<Button>(R.id.btn_d_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_D, Tone.DUR) }
        findViewById<Button>(R.id.btn_d_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_D, Tone.MOLL) }

        findViewById<Button>(R.id.btn_e_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_E, Tone.DUR) }
        findViewById<Button>(R.id.btn_e_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_E, Tone.MOLL) }

        findViewById<Button>(R.id.btn_f_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_F, Tone.DUR) }
        findViewById<Button>(R.id.btn_f_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_F, Tone.MOLL) }

        findViewById<Button>(R.id.btn_g_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_G, Tone.DUR) }
        findViewById<Button>(R.id.btn_g_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_G, Tone.MOLL) }

        findViewById<Button>(R.id.btn_a_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_A, Tone.DUR) }
        findViewById<Button>(R.id.btn_a_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_A, Tone.MOLL) }

        findViewById<Button>(R.id.btn_h_dur).setOnClickListener { presenter?.buttonPressed(Note.NOTE_H, Tone.DUR) }
        findViewById<Button>(R.id.btn_h_moll).setOnClickListener { presenter?.buttonPressed(Note.NOTE_H, Tone.MOLL) }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_stop_sound) {
            presenter?.stopMusic()
            return true
        }
        if (item?.itemId == R.id.menu_sound_settings) {
            showSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSettings() {
        SoundOptionsDialog().show(supportFragmentManager, "soundOptions")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        if (sensorOrientation != null) {
            sensorManager?.registerListener(orientationSensorListener, sensorOrientation,
                    SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        if (sensorOrientation != null) {
            sensorManager?.unregisterListener(orientationSensorListener)
        }
    }
}

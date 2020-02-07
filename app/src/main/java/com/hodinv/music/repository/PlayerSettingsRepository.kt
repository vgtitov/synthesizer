package com.hodinv.music.repository

import android.content.Context
import com.hodinv.music.model.BassMode

/**
 * Created by vasily on 23.10.17.
 */
class PlayerSettingsRepository(val context: Context) {
    val PREF_SETTINGS = "SETTINGS"
    val KEY_MODE = "MODE"
    val KEY_SIZE = "SIZE"
    val KEY_TEMPO = "TEMPO"
    var mode: BassMode
    var size: Int
    var tempo: Int

    init {
        val prefs = context.getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
        mode = BassMode.values()[prefs.getInt(KEY_MODE, BassMode.NONE.ordinal)]
        size = prefs.getInt(KEY_SIZE, 2)
        tempo = prefs.getInt(KEY_TEMPO, 120)
    }

    fun save() {
        val prefs = context.getSharedPreferences(PREF_SETTINGS, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putInt(KEY_MODE, mode.ordinal)
        edit.putInt(KEY_SIZE, size)
        edit.putInt(KEY_TEMPO, tempo)
        edit.apply()
    }

}
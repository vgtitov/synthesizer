package com.hodinv.music.screens.player.dialogs

import com.hodinv.music.model.BassMode

/**
 * Created by vasily on 23.10.17.
 */
interface SoundOptionsDialogHelper {
    companion object {
        fun fromBassModeToSpinner(mode: BassMode?): Int {
            return when (mode) {
                BassMode.NONE -> 0
                BassMode.SINGLE -> 1
                BassMode.REPEATE_TONE -> 2
                BassMode.REPTEATE_TONE_DOMINANTE -> 3
                else -> 0
            }
        }

        fun fromBassSpinnerToBassMode(pos: Int?): BassMode {
            return when (pos) {
                0 -> BassMode.NONE
                1 -> BassMode.SINGLE
                2 -> BassMode.REPEATE_TONE
                3 -> BassMode.REPTEATE_TONE_DOMINANTE
                else -> BassMode.NONE
            }
        }

        fun fromSizeToSpinner(size: Int?): Int {
            return when (size) {
                2 -> 0
                3 -> 1
                4 -> 2
                else -> 0
            }
        }

        fun fromSizeSpinnerToSizeValue(pos: Int?): Int {
            return when (pos) {
                0 -> 2
                1 -> 3
                2 -> 4
                else -> 2
            }
        }
    }
}
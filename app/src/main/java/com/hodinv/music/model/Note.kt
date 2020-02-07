package com.hodinv.music.model

/**
 * Created by vasily on 05.07.17.
 */
enum class Note(val code: String) {
    NOTE_C("C"), NOTE_D("D"), NOTE_E("E"), NOTE_F("F"), NOTE_G("G"), NOTE_A("A"), NOTE_H("H"),
    NOTE_F_UP("F_up");

    fun getDominant(): Note {
        return when (this) {
            NOTE_C -> NOTE_G
            NOTE_D -> NOTE_A
            NOTE_E -> NOTE_H
            NOTE_F -> NOTE_C
            NOTE_G -> NOTE_D
            NOTE_A -> NOTE_E
            NOTE_H -> NOTE_F_UP
            else -> NOTE_C
        }
    }
}
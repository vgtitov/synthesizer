package com.hodinv.music.services

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Handler
import android.util.Log
import com.hodinv.music.model.Note
import com.hodinv.music.model.Tone
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by vasily on 05.07.17.
 */

class MusicService(val context: Context) : MusicServiceContract {
    val handler = Handler()
    var bassPlayers = HashMap<Note, Int>()
    var chordDurPlayers = HashMap<Note, Int>()
    var chordMolPlayers = HashMap<Note, Int>()
    var prevId: Int? = null
    var prevChordId: Int? = null
    @Suppress("DEPRECATION")
    val pool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
    @Suppress("DEPRECATION")
    val chordPool = SoundPool(20, AudioManager.STREAM_MUSIC, 0)

    @Throws(IOException::class)
    private fun copyFile(input: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int = input.read(buffer)
        while (read != -1) {
            out.write(buffer, 0, read)
            read = input.read(buffer)
        }
    }

    init {

        for (note in Note.values()) {
            ensureCopied(note.code + "_bas.wav")
            ensureCopied(note.code + "_" + Tone.DUR.code + ".wav")
            ensureCopied(note.code + "_" + Tone.MOLL.code + ".wav")
            val shot = pool.load(context.getFileStreamPath(note.code + "_bas.wav").absolutePath, 1)
            val shotDur = chordPool.load(context.getFileStreamPath(note.code + "_" + Tone.DUR.code + ".wav").absolutePath, 1)
            val shotMoll = chordPool.load(context.getFileStreamPath(note.code + "_" + Tone.MOLL.code + ".wav").absolutePath, 1)
            bassPlayers.put(note, shot)
            chordDurPlayers.put(note, shotDur)
            chordMolPlayers.put(note, shotMoll)
        }
    }

    private fun ensureCopied(fileName: String) {
        if (context.getFileStreamPath(fileName)?.exists() != true) {
            // copy
            var inStream: InputStream? = null
            var outStream: OutputStream? = null
            try {
                inStream = context.assets.open(fileName)
                outStream = FileOutputStream(context.getFileStreamPath(fileName).absolutePath)
                copyFile(inStream, outStream)
            } catch (e: IOException) {
                //Log.e("tag", "Failed to copy asset file: " + filename, e)
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close()
                    } catch (e: IOException) {
                        // NOP
                    }

                }
                if (outStream != null) {
                    try {
                        outStream.close()
                    } catch (e: IOException) {
                        // NOP
                    }

                }
            }

        }
    }

    fun dimChord(streamId: Int, volume: Float) {
        chordPool.setVolume(streamId, volume, volume)
        handler.postDelayed({
            if (volume - 0.25f > 0)
                dimChord(streamId, volume - 0.25f)
            else {
                chordPool.stop(streamId)
            }
        }, 100)
    }


    override fun playCord(note: Note, tone: Tone) {
        if (prevChordId != null) {
            Log.d("PLAYER", "CHORD $prevChordId stopped")
            dimChord(prevChordId!!, 1.0f)
            //chordPool.stop(prevChordId!!)
        }
        val id = when (tone) {
            Tone.DUR -> chordDurPlayers
            else -> chordMolPlayers
        }[note]
        prevChordId = chordPool.play(id!!, 1f, 1f, 0, 0, 1f)
        Log.d("PLAYER", "CHORD $prevChordId started")
    }


    override fun playBass(note: Note) {
        if (prevId != null) {
            Log.d("PLAYER", "BASS $prevId stopped")
            pool.stop(prevId!!)
        }
        val id = bassPlayers[note]
        prevId = pool.play(id!!, 1f, 1f, 0, 0, 1f)
        Log.d("PLAYER", "BASS $prevId started")
    }

    override fun stopAll() {
        if (prevId != null) {
            Log.d("PLAYER", "BASS $prevId stopped")
            pool.stop(prevId!!)
        }
        if (prevChordId != null) {
            Log.d("PLAYER", "CHORD $prevChordId stopped")
            chordPool.stop(prevChordId!!)
        }
        prevId = null
        prevChordId = null
    }
}

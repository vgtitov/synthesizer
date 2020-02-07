package com.hodinv.music.screens.player.dialogs

import android.support.v7.widget.AppCompatSpinner
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.hodinv.music.R
import com.hodinv.music.mvp.MvpDialog


/**
 * Created by vasily on 24.08.17.
 */

class SoundOptionsDialog : MvpDialog<SoundOptionsContract.View, SoundOptionsContract.HostPresenter, SoundOptionsDialogPresenter>(), SoundOptionsContract.View {
    override fun setTempo(tempo: Int) {
        view?.findViewById<EditText>(R.id.edit_tempo)?.setText(tempo.toString(10))
    }

    override fun setSizePos(pos: Int) {
        view?.findViewById<AppCompatSpinner>(R.id.select_size)?.setSelection(pos)
    }

    override fun setBassPos(pos: Int) {
        view?.findViewById<AppCompatSpinner>(R.id.select_bass)?.setSelection(pos)
    }

    override fun createPresenter(): SoundOptionsDialogPresenter {
        return SoundOptionsDialogPresenter()
    }

    override fun getMvpView(): SoundOptionsContract.View {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_sound_options
    }

    override fun connectUi(view: View?) {
        view?.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener {
            dismiss()
        }
        view?.findViewById<Button>(R.id.btn_ok)?.setOnClickListener {
            presenter?.confirm(
                    view.findViewById<EditText>(R.id.edit_tempo).text?.toString()?.toIntOrNull(10),
                    view.findViewById<AppCompatSpinner>(R.id.select_size).selectedItemPosition,
                    view.findViewById<AppCompatSpinner>(R.id.select_bass).selectedItemPosition
            )
            dismiss()
        }
    }

}

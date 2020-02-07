package com.hodinv.music.screens.player.dialogs

import com.hodinv.music.mvp.BaseHostedPresenter

/**
 * Created by vasily on 25.08.17.
 */
class SoundOptionsDialogPresenter : BaseHostedPresenter<SoundOptionsContract.View, SoundOptionsContract.HostPresenter>(),
        SoundOptionsContract.Presenter {
    override fun confirm(tempo: Int?, size: Int?, bass: Int?) {
        hostPresenter?.setTempo(tempo ?: (hostPresenter?.getTempo() ?: 0))
        hostPresenter?.setBassMode(SoundOptionsDialogHelper.fromBassSpinnerToBassMode(bass))
        hostPresenter?.setTempoSize(SoundOptionsDialogHelper.fromSizeSpinnerToSizeValue(size))
    }

    override fun setupUi() {
        view?.setTempo(hostPresenter?.getTempo() ?: 0)
        view?.setBassPos(SoundOptionsDialogHelper.fromBassModeToSpinner(hostPresenter?.getBassMode()))
        view?.setSizePos(SoundOptionsDialogHelper.fromSizeToSpinner(hostPresenter?.getTempoSize()))
    }
}
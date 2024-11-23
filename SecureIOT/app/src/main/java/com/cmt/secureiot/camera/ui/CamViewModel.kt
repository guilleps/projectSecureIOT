package com.cmt.secureiot.camera.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CamViewModel @Inject constructor() : ViewModel() {
    val cameraUrl = "https://secureiot.espcam32.ngrok.app/stream"

}
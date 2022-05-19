package com.example.sollwar.interactivetap

import android.net.Uri
import android.widget.VideoView
import androidx.lifecycle.ViewModel

class ViewModelInteractiveFragment : ViewModel() {

    var currentVideoUri: Uri? = null
    var videoTime: Long? = null

}
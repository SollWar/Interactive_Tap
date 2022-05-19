package com.example.sollwar.interactivetap

import android.net.Uri

class VideosRepository(
    private val packageName: String
) {

    private var counter = 0

    private var videos = mutableListOf<Uri>(
        Uri.parse("android.resource://" + packageName + "/" + R.raw._1),
        Uri.parse("android.resource://" + packageName + "/" + R.raw._2),
        Uri.parse("android.resource://" + packageName + "/" + R.raw._3)
    )

    fun nextVideo(): Uri {
        if (counter == videos.size) {
            counter = 0
        }
        val videoUri = videos[counter]
        counter++
        return videoUri
    }

}

package com.app.soulstudio.view.callbacks

import com.app.soulstudio.model.dataclass.Items
import com.app.soulstudio.model.dataclass.VolumeInfo

class BookCallbacks {

    companion object {
        interface IBookCallbacks {
            fun onBookClick(position: Int, items: Items)
        }
    }
}
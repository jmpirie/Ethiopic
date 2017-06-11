package com.hundaol.ethiopic.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable

import com.hundaol.ethiopic.calendar.DiaryEntry

/**
 * Created by abinet on 6/6/17.
 */

class DiaryEntryItemViewModel(val context: Context, var diaryEntry: DiaryEntry) {
    var backgroundIndicator: Drawable? = null
}

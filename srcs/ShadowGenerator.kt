package com.osfunapps.remoteforsony.addtomodulesssss.views

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by osapps on 01/04/2022.
 *
 * This class main purpose is to assign shadow to views dynamically (on runtime).
 *
 * IMPORTANT:
 * 1) Make sure you run the python module to stream each new shadow file to this device
 * 2) Make sure you've added the permission:
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * to your Manifest
 * 3) Ask, at least once, for a runtime storage permission to change files (to fetch the shadow
 * files from the external storage). To do so, you can use askStoragePermission(Activity) function.
 **/
public class ShadowGenerator {

    // will hold the views which require shadows
    private var viewList = ArrayList<View>()

    // the current focused view
    private var currFocusedView: View? = null

    // Will add a view to the list of views which require shadow
    public fun addView(view: View) {
        view.setOnClickListener(onViewClick)
        viewList.add(view)
    }

    // Will start the sequence. Make sure you've added the views before calling this.
    // Call from your fragment/activity using launch(Main) { start(context)}
    public suspend fun start(context: Context) = withContext(Main) {
        var lastCreationTime = 0L
        var file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/shadow.9.png")
        var bitmap: Bitmap
        var bd: BitmapDrawable
        if (viewList.size > 0) {
            currFocusedView = viewList.first()
        }
        while (true) {
            val currFocusedView = this@ShadowGenerator.currFocusedView ?: continue
            if (file.exists() && fileHasChanged(file, lastCreationTime)) {

                // if the file has been changed, replace it in the view
                bitmap = BitmapFactory.decodeFile(file.path)
                bd = BitmapDrawable(context.resources, bitmap)
                currFocusedView.background = null
                println("Setting new background")
                currFocusedView.background = bd
            }
            println("Waiting 1 more sec...")
            delay(1000)
        }
    }

    // When an added view has been clicked, it will gain the focus
    private var onViewClick = View.OnClickListener {
        currFocusedView = it
    }

    private fun fileHasChanged(file: File, lastCreationTime: Long): Boolean =
        Date(file.lastModified()).time != lastCreationTime

    /**
     * Will ask for the storage runtime permission
     */
    public fun askStoragePermission(activity: Activity) {
        if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (activity.shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }
            activity.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 12);
            return;
        }
    }

}
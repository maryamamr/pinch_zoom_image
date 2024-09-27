package com.shatsy.pinchzoomimage

import android.app.Activity
import android.view.View
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry.Registrar

class PinchZoomImagePlugin(private val activity: Activity) : MethodCallHandler {

    // Store the original System UI Visibility of the activity.
    private val originalSystemUiVisibility = activity.window?.decorView?.systemUiVisibility ?: 0

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            // Check if the activity is null and handle the error.
            val activity = registrar.activity()
            if (activity != null) {
                val channel = MethodChannel(registrar.messenger(), "pinch_zoom_image")
                channel.setMethodCallHandler(PinchZoomImagePlugin(activity))
            } else {
                // Log or handle the case when activity is null.
                println("PinchZoomImagePlugin: Unable to register plugin, activity is null.")
            }
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "hideStatusBar" -> {
                activity.window?.decorView?.systemUiVisibility = originalSystemUiVisibility or
                    View.SYSTEM_UI_FLAG_FULLSCREEN and
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv() and
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv()
                result.success(null)
            }
            "showStatusBar" -> {
                activity.window?.decorView?.systemUiVisibility = originalSystemUiVisibility
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }
}

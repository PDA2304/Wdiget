package com.example.widget

import android.Manifest
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContextCompat

const val widget: String = "But"
var poweroff: Boolean = false
class CameraWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!poweroff) {

            if (!context!!.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Toast.makeText(context, "Нет камеры", Toast.LENGTH_SHORT).show()
                return
            }

            if ((ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(context, "Нет разрешения на камеру", Toast.LENGTH_SHORT).show()
                return
            }

            if ((context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager) == null) {
                Toast.makeText(context, "Нет камеры", Toast.LENGTH_SHORT).show()
                return
            }
            (context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager).
            setTorchMode((context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager).cameraIdList[0], true
            )
            poweroff = true

        } else {
            (context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager).
            setTorchMode((context!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager).cameraIdList[0], false)
            poweroff = false
        }

        updateAppWidget(context!!,AppWidgetManager.getInstance(context),intent!!.getIntExtra("appWidgetId", 0))

        super.onReceive(context, intent)
    }
}

internal fun updateAppWidget(context: Context,appWidgetManager: AppWidgetManager,appWidgetId: Int) {

    val intent: Intent = Intent(context, CameraWidget::class.java)
        val views = RemoteViews(context.packageName, R.layout.camera_widget)

    intent.action = widget
    intent.putExtra("appWidgetId", appWidgetId)
    views.setOnClickPendingIntent(R.id.btn_flashlight, PendingIntent.getBroadcast(context, 0, intent, 0))

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
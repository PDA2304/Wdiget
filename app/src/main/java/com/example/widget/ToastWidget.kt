package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
class ToastWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context,appWidgetManager: AppWidgetManager,appWidgetIds: IntArray) {
        // Там может быть несколько активных виджетов, поэтому обновите их все
        for (appWidgetId in appWidgetIds) {
            updateAppWidget1(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Введите соответствующую функциональность для момента создания первого виджета
    }

    override fun onDisabled(context: Context) {
        // Введите соответствующую функциональность для тех случаев, когда последний виджет отключен
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        Toast.makeText(context, "hehdl", Toast.LENGTH_SHORT).show()
        updateAppWidget(context!!,AppWidgetManager.getInstance(context),intent!!.getIntExtra("appWidgetId", 0))
    }
}

internal fun updateAppWidget1(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent: Intent = Intent(context, ToastWidget::class.java)
    val widgetText = context.getString(R.string.appwidget_text)
    // Создание объекта RemoteViews
    val views = RemoteViews(context.packageName, R.layout.toast_widget)
    intent.putExtra("appWidgetId", appWidgetId)
    //обработчик нажатии кнопки
    views.setOnClickPendingIntent(R.id.dialog_button, PendingIntent.getBroadcast(context, 0,intent, 0))

    // Поручите менеджеру виджетов обновить виджет
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
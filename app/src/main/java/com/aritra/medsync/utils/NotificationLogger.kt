package com.aritra.medsync.utils

import timber.log.Timber

object NotificationLogger {
    private const val TAG = "MedSyncNotification"

    fun debug(message: String) {
        Timber.tag(TAG).d(message)
    }

    fun error(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.tag(TAG).e(throwable, message)
        } else {
            Timber.tag(TAG).e(message)
        }
    }
}
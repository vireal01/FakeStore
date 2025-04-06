package com.vireal.fakestore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FakeStoreApplication: Application() {
  override fun onCreate() {
    super.onCreate()
  }
}
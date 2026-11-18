package com.example.shoplist

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun packageAndAppLabelIdentifyTheVariant() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val flavor = BuildConfig.FLAVOR
        val buildType = BuildConfig.BUILD_TYPE

        assertEquals("com.example.shoplist.$flavor.$buildType", appContext.packageName)
        assertEquals("shoplist ($flavor $buildType)", appContext.getString(R.string.app_name))
    }
}

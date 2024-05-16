package com.example.foodorderingapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Инструментальный тест, который будет выполняться на устройстве Android.
 *
 * @see <a href="http://d.android.com/tools/testing">Документация по тестированию</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Контекст тестируемого приложения.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.foodorderingapp", appContext.getPackageName());
    }
}

package com.example.shoplist

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoplist.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShopListInteractionTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun addEditToggleAndSwipeLeft() {
        addItem("Тестова покупка", "3")
        compose.onNodeWithText("Тестова покупка").performScrollTo()
        compose.onNode(hasText("Тестова покупка") and hasText("Активна")).assertExists()

        compose.onNodeWithText("Тестова покупка").performTouchInput { longClick() }
        compose.onNode(hasText("Тестова покупка") and hasText("Неактивна")).assertExists()

        compose.onNodeWithText("Тестова покупка").performClick()
        nameField().performTextReplacement("Оновлена покупка")
        countField().performTextReplacement("7")
        compose.onNodeWithText("Зберегти").performClick()
        compose.onNodeWithText("Оновлена покупка").performScrollTo()
        compose.onNode(
            hasText("Оновлена покупка") and hasText("Кількість: 7") and hasText("Неактивна")
        ).assertExists()
        compose.onNodeWithText("Тестова покупка").assertDoesNotExist()

        compose.onNodeWithText("Оновлена покупка").performTouchInput { swipeLeft() }
        compose.waitForIdle()
        compose.onNodeWithText("Оновлена покупка").assertDoesNotExist()
    }

    @Test
    fun swipeRightDeletesItem() {
        addItem("Свайп вправо", "1")
        compose.onNodeWithText("Свайп вправо").performScrollTo()
        compose.onNodeWithText("Свайп вправо").performTouchInput { swipeRight() }
        compose.waitForIdle()
        compose.onNodeWithText("Свайп вправо").assertDoesNotExist()
    }

    @Test
    fun invalidInputAndCancelDoNotSaveItem() {
        compose.onNodeWithText("Додати покупку").performClick()
        nameField().performTextReplacement("   ")
        countField().performTextReplacement("0")
        compose.onNodeWithText("Зберегти").performClick()
        compose.onNodeWithText("Введіть назву покупки").assertExists()
        compose.onNodeWithText("Введіть ціле число більше нуля").assertExists()

        nameField().performTextReplacement("Скасована покупка")
        countField().performTextReplacement("2")
        compose.onNodeWithText("Скасувати").performClick()
        compose.onNodeWithText("Скасована покупка").assertDoesNotExist()
    }

    private fun addItem(name: String, count: String) {
        compose.onNodeWithText("Додати покупку").performClick()
        nameField().performTextReplacement(name)
        countField().performTextReplacement(count)
        compose.onNodeWithText("Зберегти").performClick()
    }

    private fun nameField() = compose.onNode(hasSetTextAction() and hasText("Назва"))

    private fun countField() = compose.onNode(hasSetTextAction() and hasText("Кількість"))
}

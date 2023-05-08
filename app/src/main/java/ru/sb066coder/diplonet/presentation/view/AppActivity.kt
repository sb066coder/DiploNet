package ru.sb066coder.diplonet.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.sb066coder.diplonet.R

/**
 * Запуск приложения
 * Экран ввода логина и пароля
 * Навигация верхнего уровня
 * - Экран ленты постов
 * --- Детальная информация о посте
 * --- Создать новый пост
 * --- Редактировать пост (только свой)
 * - Экран ленты событий
 * --- Детальная информация о событии
 * - Экран пользователей
 * --- Информация о пользователе
 * ----- Детальная информация о месте работы
 * --- Лента постов пользователя
 */

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}
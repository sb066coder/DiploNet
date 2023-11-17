package ru.sb066coder.diplonet.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.ActivityAppBinding
import ru.sb066coder.diplonet.presentation.viewmodel.AuthViewModel

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

    private lateinit var binding: ActivityAppBinding

    private val viewModel : AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) {
            if (viewModel.authenticated) {
                binding.bottomNavView.visibility = View.VISIBLE
                val navView: BottomNavigationView = binding.bottomNavView
                val navController = this@AppActivity.findNavController(R.id.nav_host_fragment)
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.navigation_post_feed,
                        R.id.navigation_event_feed,
                        R.id.navigation_logout
                    )
                )
                this@AppActivity.setupActionBarWithNavController(navController, appBarConfiguration)
                navView.setupWithNavController(navController)
            } else {
                binding.bottomNavView.visibility = View.GONE
            }
        }
    }
}
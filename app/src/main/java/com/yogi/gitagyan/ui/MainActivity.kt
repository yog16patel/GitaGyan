package com.yogi.gitagyan.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.yogi.domain.repository.SharedPreferencesRepository
import com.yogi.gitagyan.appconfig.LanguageChangeUtil
import com.yogi.gitagyan.ui.theme.Background
import com.yogi.gitagyan.ui.viewmodels.GitaGyanViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var viewModel: GitaGyanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[GitaGyanViewModel::class.java]
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Background
            ) {
                GitaGyanApp(viewModel = viewModel)
            }
        }

    }
}


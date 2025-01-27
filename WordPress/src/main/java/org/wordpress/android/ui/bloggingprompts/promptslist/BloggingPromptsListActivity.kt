package org.wordpress.android.ui.bloggingprompts.promptslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.android.ui.compose.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class BloggingPromptsListActivity : AppCompatActivity() {
    @Inject lateinit var tracker: BloggingPromptsListAnalyticsTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                BloggingPromptsListScreen(::onBackPressed)
            }
        }
        tracker.trackScreenShown()
    }

    // TODO it might be safer bringing in the androidx.activity:activity-compose lib
    private fun setContent(content: @Composable () -> Unit) {
        val composeView = ComposeView(this).apply { setContent(content) }
        setContentView(composeView)
    }

    companion object {
        @JvmStatic
        fun createIntent(context: Context) = Intent(context, BloggingPromptsListActivity::class.java)
    }
}

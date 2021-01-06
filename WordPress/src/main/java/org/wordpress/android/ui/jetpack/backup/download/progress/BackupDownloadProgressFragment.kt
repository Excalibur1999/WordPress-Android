package org.wordpress.android.ui.jetpack.backup.download.progress

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.wordpress.android.R
import org.wordpress.android.WordPress
import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.ui.jetpack.backup.download.BackupDownloadState
import org.wordpress.android.ui.jetpack.backup.download.BackupDownloadViewModel
import javax.inject.Inject

private const val ARG_DATA = "arg_backup_download_progress_data"

class BackupDownloadProgressFragment : Fragment(R.layout.backup_download_progress_fragment) {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var parentViewModel: BackupDownloadViewModel
    private lateinit var viewModel: BackupDownloadProgressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initDagger() {
        (requireActivity().application as WordPress).component().inject(this)
    }

    private fun initViewModel() {
        parentViewModel = ViewModelProvider(requireActivity(), viewModelFactory)
                .get(BackupDownloadViewModel::class.java)

        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(BackupDownloadProgressViewModel::class.java)

        val (site, state) = when {
            arguments != null -> {
                val site = requireNotNull(arguments).getSerializable(WordPress.SITE) as SiteModel
                val state = requireNotNull(arguments)
                        .getParcelable<BackupDownloadState>(ARG_DATA) as BackupDownloadState
                site to state
            }
            else -> throw Throwable("Couldn't initialize ${javaClass.simpleName} view model")
        }

        viewModel.start(site, state, parentViewModel)
    }

    companion object {
        const val TAG = "BACKUP_DOWNLOAD_PROGRESS_FRAGMENT"
        fun newInstance(
            bundle: Bundle? = null,
            backupDownloadState: BackupDownloadState
        ): BackupDownloadProgressFragment {
            val newBundle = Bundle().apply {
                putParcelable(ARG_DATA, backupDownloadState)
            }
            bundle?.let { newBundle.putAll(bundle) }
            return BackupDownloadProgressFragment().apply { arguments = newBundle }
        }
    }
}
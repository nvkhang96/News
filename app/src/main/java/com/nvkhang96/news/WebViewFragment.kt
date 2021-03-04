package com.nvkhang96.news

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.nvkhang96.news.api.VnexpressService
import com.nvkhang96.news.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var binding: FragmentWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)

        with(binding.webView) {
            val nightModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightModeFlag == Configuration.UI_MODE_NIGHT_YES) {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        settings,
                        WebSettingsCompat.FORCE_DARK_ON
                    )
                }

                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
                    WebSettingsCompat.setForceDarkStrategy(
                        settings,
                        WebSettingsCompat.DARK_STRATEGY_PREFER_WEB_THEME_OVER_USER_AGENT_DARKENING
                    )
                }
            }

            webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (Uri.parse(url).host == "vnexpress.net") {
                        return false
                    }
                    Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                        startActivity(this)
                    }
                    return true
                }
            }

            webChromeClient = object : WebChromeClient() {

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.progress.progress = progress
                    if (progress == 100) {
                        binding.progress.visibility = View.GONE
                    } else {
                        binding.progress.visibility = View.VISIBLE
                    }
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(args.link)
        }

        with(binding.toolbar) {

            setNavigationOnClickListener { view->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navCtrler = Navigation.findNavController(view)

        requireActivity().onBackPressedDispatcher.addCallback {
            if (navCtrler.currentDestination?.id == R.id.web_view_fragment && binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                if (!navCtrler.popBackStack()) {
                    activity?.finish()
                    exitProcess(0)
                }
            }
        }
    }

}
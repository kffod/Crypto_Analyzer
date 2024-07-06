package com.example.cryptocurrencyanalizer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.cryptocurrencyanalizer.R

class TradeViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_trade_view, container, false)

        val webView: WebView = rootView.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://cryptopredictions.com/bitcoin/?year=2023")

        return rootView
    }
}

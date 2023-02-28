package com.explwa.jexchangeanalytics.app.module.token.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.app.module.token.utils.Utils
import com.explwa.jexchangeanalytics.app.utils.BaseActivity
import com.explwa.jexchangeanalytics.databinding.ActivityTokenBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.viewModels.TokenViewModel
import com.tradingview.lightweightcharts.api.interfaces.SeriesApi
import com.tradingview.lightweightcharts.api.options.models.localizationOptions
import com.tradingview.lightweightcharts.api.series.models.HistogramData
import com.tradingview.lightweightcharts.api.series.models.Time
import com.tradingview.lightweightcharts.api.series.models.WhitespaceData
import com.tradingview.lightweightcharts.runtime.plugins.DateTimeFormat
import com.tradingview.lightweightcharts.runtime.plugins.PriceFormatter
import com.tradingview.lightweightcharts.runtime.plugins.TimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class TokenActivity : BaseActivity() {

    lateinit var binding : ActivityTokenBinding

    val viewModel : TokenViewModel by viewModels()

    private var linkSectionVisible = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.chartsView.api.applyOptions {
            localization = localizationOptions {
                locale = "fr-FR"
                priceFormatter = PriceFormatter(template = "{price:#2:#3}$")
                timeFormatter = TimeFormatter(
                    locale = "fr-FR",
                    dateTimeFormat = DateTimeFormat.DATE_TIME
                )
            }
        }

        val data = listOf(
            HistogramData(Time.BusinessDay(2019, 6, 11), 40.01f),
            HistogramData(Time.BusinessDay(2019, 6, 12), 52.38f),
            HistogramData(Time.BusinessDay(2019, 6, 13), 36.30f),
            HistogramData(Time.BusinessDay(2019, 6, 14), 34.48f),
            WhitespaceData(Time.BusinessDay(2019, 6, 15)),
            WhitespaceData(Time.BusinessDay(2019, 6, 16)),
            HistogramData(Time.BusinessDay(2019, 6, 17), 41.50f),
            HistogramData(Time.BusinessDay(2019, 6, 18), 34.82f)
        )

        lateinit var histogramSeries: SeriesApi
        binding.chartsView.api.addHistogramSeries(
            onSeriesCreated = { series ->
                histogramSeries = series
                histogramSeries.setData(data)
            }
        )

        updateUi(TokenViewModel.ShowLoading())
        configToolbar()

        val idToken = intent.getStringExtra("IDTOKEN")

        idToken?.let { id ->
            viewModel.getViewState(id)
                .subscribeBy { state ->
                    updateUi(state)
                }.addTo(disposable)
        }


    }

    private fun updateUi(state: TokenViewModel.ViewState) {
        when(state) {
            is TokenViewModel.ShowToken -> {
                bind(state.token)
                binding.constraintLayoutToken.isGone = !state.progressBarGone
                binding.progressBar.isGone = state.progressBarGone
            }
            is TokenViewModel.ShowLoading -> {
                binding.constraintLayoutToken.isGone = !state.progressBarGone
                binding.progressBar.isGone = state.progressBarGone
            }
            is TokenViewModel.ShowError -> {
                Toast.makeText(
                    this,
                    state.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun bind(token: UITokenItem.Cell) {
        with(binding) {

            // Token Name
            textviewNameToken.text = token.name
            textviewNameToken.text = token.name?.replaceFirstChar(Char::uppercase)

            // Token Owner
            textviewOwner.text = Utils.formatAddress(token.owner.toString())
            linearLayoutOwner.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://explorer.multiversx.com/accounts/" + token.owner)
                    )
                )
            }

            // Token Properties
            textviewIsPaused.text = Utils.formatBooleanProperties(token.isPaused.toString())
            textviewCanBurn.text = Utils.formatBooleanProperties(token.canBurn.toString())
            textviewCanChangeOwner.text = Utils.formatBooleanProperties(token.canChangeOwner.toString())
            textviewCanFreeze.text = Utils.formatBooleanProperties(token.canFreeze.toString())
            textviewCanMint.text = Utils.formatBooleanProperties(token.canMint.toString())
            textviewCanPause.text = Utils.formatBooleanProperties(token.canPause.toString())
            textviewCanUpgrade.text = Utils.formatBooleanProperties(token.canUpgrade.toString())
            textviewCanWipe.text = Utils.formatBooleanProperties(token.canWipe.toString())

            // Token Statistics
            textviewTransactions.text = token.transactions.toString()
            textviewAccounts.text = token.accounts.toString()
            textviewMarketcap.text = Utils.toFormatString(token.marketCap.toString())
            textviewCirculatingSupply.text = token.circulatingSupply
            textviewSupply.text = token.supply
            textviewPriceToken.text = Utils.toFormatString(token.price.toString())
            textviewTokenIdentifier.text = token.identifier

            // Token Links
            hideEmptySection(textviewBlog, token.blog)
            hideEmptySection(textviewWebsite, token.website)
            hideEmptySection(textviewCoingecko, token.coingecko)
            hideEmptySection(textviewCoinmarketcap, token.coinmarketcap)
            hideEmptySection(textviewWhitepaper, token.whitepaper)
            hideEmptySection(textviewTwitter, token.twitter)
            hideEmptySection(textviewEmail, token.email)
            if (linkSectionVisible == NB_LINKS_SECTION) materialCardViewLinks.isGone = true

            // Static Textview
            textviewIdentifierInStats.text = Utils.getSortNameToken(token.identifier.toString())
            textviewIdentifierInProperties.text = Utils.getSortNameToken(token.identifier.toString())

            // Token Image
            if (token.pngUrl != null) {
                Glide.with(this@TokenActivity).load(token.pngUrl).into(imageviewMainTokenLogo)
                Glide.with(this@TokenActivity).load(token.pngUrl).into(imageViewTokenLogo)
            } else {
                imageviewMainTokenLogo.isGone = true
                imageViewTokenLogo.isGone = true
            }
        }
    }

    private fun hideEmptySection(
        textView: TextView,
        tokenProperty: String?
    ) : Any =
        if(tokenProperty == null) {
            textView.isGone = true
            linkSectionVisible++
        } else {
            textView.text = tokenProperty.toString()
            linkSectionVisible = 0
        }

    private fun configToolbar() {
        setSupportActionBar(binding.topToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val NB_LINKS_SECTION = 7
    }
}
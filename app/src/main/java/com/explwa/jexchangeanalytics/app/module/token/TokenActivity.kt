package com.explwa.jexchangeanalytics.app.module.token

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.app.module.token.utils.Utils
import com.explwa.jexchangeanalytics.databinding.ActivityTokenBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.viewModels.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class TokenActivity : AppCompatActivity() {

    lateinit var binding : ActivityTokenBinding

    val viewModel : TokenViewModel by viewModels()

    var showLinkSection = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateUi(TokenViewModel.ShowLoading())
        configToolbar()

        val idToken = intent.getStringExtra("IDTOKEN")

        idToken?.let { id ->
            viewModel.getViewState(id)
                .subscribeBy { state ->
                    updateUi(state)
                }
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
            hideEmptySection(linearLayoutBlog, token.blog, textviewBlog)
            hideEmptySection(linearLayoutWebsite, token.website, textviewWebsite)
            hideEmptySection(linearLayoutCoingecko, token.coingecko, textviewCoingecko)
            hideEmptySection(linearLayoutCoinmarketcap, token.coinmarketcap, textviewCoinmarketcap)
            hideEmptySection(linearLayoutWhitepaper, token.whitepaper, textviewWhitepaper)
            hideEmptySection(linearLayoutTwitter, token.twitter, textviewTwitter)
            hideEmptySection(linearLayoutEmail, token.email, textviewEmail)
            if (showLinkSection == 7) materialCardViewLinks.isGone = true

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
        linearLayout: LinearLayout,
        tokenProperty: String?,
        textView: TextView
    ) : Any =
        if(tokenProperty == null) {
            linearLayout.isGone = true
            showLinkSection++
        } else {
            textView.text = tokenProperty.toString()
            showLinkSection = 0
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
}
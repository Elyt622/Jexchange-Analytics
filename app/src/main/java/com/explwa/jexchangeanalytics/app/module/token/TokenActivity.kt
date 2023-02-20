package com.explwa.jexchangeanalytics.app.module.token

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.app.module.token.utils.Utils
import com.explwa.jexchangeanalytics.databinding.ActivityTokenBinding
import com.explwa.jexchangeanalytics.presenter.model.UITokenItem
import com.explwa.jexchangeanalytics.presenter.model.mapping.toUIItem
import com.explwa.jexchangeanalytics.presenter.viewModels.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.subscribeBy


@AndroidEntryPoint
class TokenActivity : AppCompatActivity() {

    lateinit var binding : ActivityTokenBinding

    val viewModel : TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTokenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configToolbar()

        val idToken = intent.getStringExtra("IDTOKEN")

        idToken?.let { id ->
            viewModel.getTokenDetails(id)
                .map { token -> token.toUIItem() as UITokenItem.Cell }
                .subscribeBy { token -> bind(token) }
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
            hideNullSection(linearLayoutBlog, token.blog, textviewBlog)
            hideNullSection(linearLayoutWebsite, token.website, textviewWebsite)
            hideNullSection(linearLayoutCoingecko, token.coingecko, textviewCoingecko)
            hideNullSection(linearLayoutCoinmarketcap, token.coinmarketcap, textviewCoinmarketcap)
            hideNullSection(linearLayoutWhitepaper, token.whitepaper, textviewWhitepaper)
            hideNullSection(linearLayoutTwitter, token.twitter, textviewTwitter)
            hideNullSection(linearLayoutEmail, token.email, textviewEmail)

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

    fun hideNullSection(
        linearLayout: LinearLayout,
        string: String?,
        textView: TextView
    ) {
        if(string == null)
            linearLayout.isGone = true
        else
            textView.text = string.toString()
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
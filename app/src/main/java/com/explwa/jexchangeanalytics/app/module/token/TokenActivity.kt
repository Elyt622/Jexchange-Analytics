package com.explwa.jexchangeanalytics.app.module.token

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.explwa.jexchangeanalytics.databinding.ActivityTokenBinding
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
                .subscribeBy {token ->
                    with(binding) {
                        textviewNameToken.text = token.name
                        textviewNameToken.text = token.name?.replaceFirstChar(Char::uppercase)

                        textviewAccounts.text = token.accounts.toString()
                        textviewBlog.text = token.blog
                        textviewMarketcap.text = token.marketCap.toString()
                        textviewCirculatingSupply.text = token.circulatingSupply
                        textviewSupply.text = token.supply
                        textviewWebsite.text = token.website
                        textviewPriceToken.text = token.price.toString()
                        textviewTokenIdentifier.text = token.identifier

                        textviewCoingecko.text = token.coingecko
                        textviewCoinmarketcap.text = token.coinmarketcap

                        textviewWhitepaper.text = token.whitepaper
                        textviewTwitter.text = token.twitter

                        textviewEmail.text = token.email

                        Glide.with(this@TokenActivity).load(token.pngUrl).into(imageviewMainTokenLogo)
                        Glide.with(this@TokenActivity).load(token.pngUrl).into(imageViewTokenLogo)
                    }
                }
        }
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
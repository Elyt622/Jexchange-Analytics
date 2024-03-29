package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.BurnEsdtUsecase
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BurnEsdtUsecaseTest {

    private val burnEsdtUsecase = BurnEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded`() {
        val transaction = burnEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            supplyToBurn = "10".toBigInteger(),
        )

        assertEquals(
            "ESDTBurn@4552444b54363937322d623665643261@0a",
            transaction.data
        )

    }

}

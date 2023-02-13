package com.explwa.jexchangeanalytics.domain.repositories

import com.explwa.jexchangeanalytics.domain.models.DomainToken
import io.reactivex.rxjava3.core.Single

interface TokenRepository {

    fun getTokenDetails(idToken: String) : Single<DomainToken>

}
package com.explwa.jexchangeanalytics.app.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

open class BaseFragment : Fragment() {

    protected var viewCreatedSubject: BehaviorSubject<Unit> = BehaviorSubject.create()

    protected val disposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreatedSubject.onNext(Unit)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

}
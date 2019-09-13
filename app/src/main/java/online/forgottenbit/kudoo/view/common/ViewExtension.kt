package online.forgottenbit.kudoo.view.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.view.ViewManager
import kotlin.reflect.KClass

fun <T: ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T = ViewModelProviders.of(this).get(modelClass.java)
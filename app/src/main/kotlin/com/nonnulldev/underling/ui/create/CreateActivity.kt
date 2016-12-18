package com.nonnulldev.underling.ui.create

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import butterknife.*
import com.nonnulldev.underling.R
import com.nonnulldev.underling.ui.base.BaseActivity
import com.nonnulldev.underling.ui.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateActivity : BaseActivity() {

    @BindView(R.id.btnCreate)
    lateinit var btnCreate: Button

    @BindView(R.id.etPlayerName)
    lateinit var etPlayerName: EditText


    @Inject
    lateinit protected var viewModel: CreateScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        ButterKnife.bind(this)

        getActivityComponent().inject(this)

        initBindings()
    }

    private fun initBindings() {
        subscriptions.addAll(
                viewModel.getPlayerNameIsValidObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it -> playerNameIsValid(it) },

                viewModel.getPlayerWasCreatedObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it ->  playerWasCreated(it) }
        )
    }

    fun playerNameIsValid(nameIsValid: Boolean) {
        btnCreate.isEnabled = nameIsValid
    }

    fun playerWasCreated(created: Boolean) {
        if(created) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @OnTextChanged(R.id.etPlayerName)
    fun onPlayerNameEditTextChanged(name: CharSequence) {
        viewModel.validatePlayerName(name.toString())
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    @OnClick(R.id.btnCreate)
    fun onCreateButtonClicked() {
        viewModel.createPlayer(etPlayerName.text.toString())
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}

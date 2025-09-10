package com.example.authsystem.presentation.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.authsystem.R
import com.example.authsystem.databinding.RegisterBinding
import com.example.authsystem.domain.data.User
import com.example.authsystem.presentation.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindListener()
        bindObserver()
    }

    private fun bindListener() {
        binding.textButton.setOnClickListener {
            val name = binding.name.editText?.text.toString()
            val email = binding.email.editText?.text.toString()
            val password = binding.password.editText?.text.toString()
            val user = User(name, email, password)

            viewModel.registerUser(user)
        }

        binding.password.setEndIconOnClickListener {
            val editText = binding.password.editText
            editText?.let {
                if (it.inputType and InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ==
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ) {
                    it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.password.endIconDrawable = getDrawable(R.drawable.visibility_off)
                } else {
                    it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    binding.password.endIconDrawable = getDrawable(R.drawable.visibility)
                }
                it.setSelection(it.text?.length ?: 0)
            }
        }

        binding.loadin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindObserver() {
        viewModel.state.observe(this) {
            setDefaultState()
            when (it) {
                RegisterState.Loading -> showLoadingScreen()
                is RegisterState.Success -> showSuccessScreen()
                is RegisterState.Error -> {
                    when (it.field) {
                        "NameEmpty" -> showErrorNameEmptyScreen(it.message)
                        "EmailEmpty" -> showErrorEmailEmptyScreen(it.message)
                        "PasswordEmpty" -> showErrorPasswordEmptyScreen(it.message)
                        "EmailInvalid" -> showErrorEmailScreen(it.message)
                        "PasswordInvalid" -> showErrorPasswordScreen(it.message)
                        "EmailAlreadyExists" -> showErrorEmailAlreadyExists(it.message)
                        else -> showErrorGeneral(it.message)
                    }
                }
            }
        }
    }

    private fun setDefaultState() {
        binding.name.error = null
        binding.email.error = null
        binding.password.error = null
        binding.stateLoading.root.isVisible = false
        binding.name.isErrorEnabled = false
        binding.email.isErrorEnabled = false
        binding.password.isErrorEnabled = false
        binding.formContainer.isVisible = false
    }

    private fun showLoadingScreen() {
        binding.formContainer.isVisible = false
        binding.stateLoading.root.isVisible = true
    }

    private fun showErrorNameEmptyScreen(message: String) {
        binding.formContainer.isVisible = true
        binding.name.isErrorEnabled = true
        binding.name.error = message
    }

    private fun showErrorEmailEmptyScreen(message: String) {
        binding.formContainer.isVisible = true
        binding.email.isErrorEnabled = true
        binding.email.error = message
    }

    private fun showErrorPasswordEmptyScreen(message: String) {
        binding.formContainer.isVisible = true
        binding.password.isErrorEnabled = true
        binding.password.error = message
    }

    private fun showErrorEmailScreen(message: String) {
        binding.formContainer.isVisible = true
        binding.email.isErrorEnabled = true
        binding.email.error = message
    }

    private fun showErrorPasswordScreen(message: String) {
        binding.formContainer.isVisible = true
        binding.password.isErrorEnabled = true
        binding.password.error = message
    }

    private fun showErrorEmailAlreadyExists(message: String) {
        binding.formContainer.isVisible = true
        binding.email.isErrorEnabled = true
        binding.email.error = message

        Toast.makeText(
            this@RegisterActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorGeneral(message: String) {
        binding.formContainer.isVisible = true
        binding.email.isErrorEnabled = true
        binding.email.error = message
        binding.name.isErrorEnabled = true
        binding.password.isErrorEnabled = true

        Toast.makeText(
            this@RegisterActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showSuccessScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
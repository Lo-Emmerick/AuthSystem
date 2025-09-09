package com.example.authsystem.presentation.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
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

        bindObserver()
        bindListener()
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
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
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
                is RegisterState.Error -> {
                    when (it.message) {
                        "NameEmpty" -> {showErrorNameEmptyScreen()}
                        "EmailEmpty" -> {showErrorEmailEmptyScreen()}
                        "PasswordEmpty" -> {showErrorPasswordEmptyScreen()}
                        "EmailInvalid" -> {showErrorEmailScreen()}
                        "PasswordInvalid" -> {showErrorPasswordScreen()}
                        "PasswordInvalid" -> {showErrorPasswordScreen()}

                        else -> {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Erro ao concluir o cadastro. Tente novamente mais tarde",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                is RegisterState.Success -> showSuccessScreen()
            }
        }
    }

    private fun setDefaultState() {
        binding.name.error = null
        binding.loadin.isVisible = false
        binding.name.isErrorEnabled = false
        binding.email.isErrorEnabled = false
        binding.password.isErrorEnabled = false
    }

    private fun showLoadingScreen() {
        binding.loadin.isVisible = true
    }

    private fun showErrorNameEmptyScreen() {
        binding.password.isErrorEnabled = true
        binding.name.error = "Nome não pode ficar vazio"
    }

    private fun showErrorEmailEmptyScreen() {
        binding.password.isErrorEnabled = true
        binding.email.error = "Email não pode ficar vazio"
    }

    private fun showErrorPasswordEmptyScreen() {
        binding.password.isErrorEnabled = true
        binding.password.error = "Senha não pode ficar vazio"
    }

    private fun showErrorEmailScreen() {
        binding.password.isErrorEnabled = true
        binding.email.error = "Digite um email válido"
    }

    private fun showErrorPasswordScreen() {
        binding.password.isErrorEnabled = true
        binding.password.error = "Mínimo 6 caracteres"
    }

    private fun showSuccessScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
package com.delet_dis.madmeditation

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.delet_dis.madmeditation.databinding.ActivityLoginBinding
import com.delet_dis.madmeditation.helpers.AlertDialogHelper
import com.delet_dis.madmeditation.helpers.WindowHelper
import com.delet_dis.madmeditation.model.LoginRequest
import com.delet_dis.madmeditation.model.LoginResponse
import com.delet_dis.madmeditation.repositories.ConstantsRepository
import com.delet_dis.madmeditation.repositories.RetrofitRepository
import com.delet_dis.madmeditation.repositories.SharedPreferencesRepository
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowHelper.setWindowNoLimits(this)

    setContentView(createViewBinding())

    fillEmailFieldIfEmailIsNotNull()

    registerLoginButtonOnclick()

    registerRegisterButtonOnclick()
  }

  private fun fillEmailFieldIfEmailIsNotNull() {
    if (SharedPreferencesRepository(applicationContext).getEmail() != null) {
      binding.emailInputField.setText(SharedPreferencesRepository(applicationContext).getEmail())
    }
  }

  private fun createViewBinding(): ConstraintLayout {
    binding = ActivityLoginBinding.inflate(layoutInflater)
    return binding.root
  }

  private fun registerRegisterButtonOnclick() {
    binding.noAccountHintRegistration.setOnClickListener {
      val intent = Intent(this, RegistrationActivity::class.java)
      startActivity(intent)
    }
  }

  private fun registerLoginButtonOnclick() {
    binding.loginButton.setOnClickListener {
      checkCorrectnessOfFields()
    }
  }

  private fun checkCorrectnessOfFields() {
    if (isEmailCorrect(binding.emailInputField.text.toString()) &&
      binding.passwordInputField.text.toString().isNotBlank()
    ) {

      val loginRequest = makeLoginRequest()

      postLoginData(loginRequest)

    } else {
      AlertDialogHelper.buildAlertDialog(
        this,
        R.string.alertDialogDataIncorrectMessage
      )
    }
  }

  private fun retrofitOnResponse(response: Response<LoginResponse>) {

    val loginRequest = makeLoginRequest();

    if (response.errorBody() !== null) {
      AlertDialogHelper.buildAlertDialog(
        this, R.string.alertDialogLoginFailedMessage
      )
    } else {
      SharedPreferencesRepository(applicationContext).setLoginState(true)

      SharedPreferencesRepository(applicationContext).setLoginData(
        loginRequest.email,
        loginRequest.password
      )

      val processingIntent = Intent(this, MainActivity::class.java)
      processingIntent.putExtra(ConstantsRepository.loginResponseParcelableName, response.body())
      startActivity(processingIntent)
      finish()
    }
  }

  private fun retrofitOnFailure() {
    AlertDialogHelper.buildAlertDialog(
      this, R.string.networkErrorMessage
    )
  }

  private fun postLoginData(loginRequest: LoginRequest) {
    RetrofitRepository.postLoginData(loginRequest, ::retrofitOnResponse, ::retrofitOnFailure)
  }

  private fun makeLoginRequest() = LoginRequest(
    binding.emailInputField.text.toString(),
    binding.passwordInputField.text.toString()
  )

  private fun isEmailCorrect(processingEmail: String): Boolean {
    return !TextUtils.isEmpty(processingEmail) && android.util.Patterns.EMAIL_ADDRESS.matcher(
      processingEmail
    ).matches()
  }
}
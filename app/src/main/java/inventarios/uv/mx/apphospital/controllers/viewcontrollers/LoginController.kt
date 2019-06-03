package inventarios.uv.mx.apphospital.controllers.viewcontrollers

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnClick
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import inventarios.uv.mx.apphospital.R
import inventarios.uv.mx.apphospital.controllers.viewcontrollers.generics.GenericController
import inventarios.uv.mx.apphospital.model.entities.User
import inventarios.uv.mx.apphospital.model.utils.LogUtils
import inventarios.uv.mx.apphospital.model.utils.Responses
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class LoginController: GenericController() {

    override var debugTag = LoginController::class.java.simpleName
    override var controllerId = R.layout.controller_login

    @BindView(R.id.viewLoading)
    lateinit var  viewLoading : LinearLayout

    @BindView(R.id.buttonLogin)
    lateinit var buttonLogin: Button

    @BindView(R.id.textFieldUsername)
    lateinit var txtFieldUsername: TextInputLayout

    @BindView(R.id.textFieldPassword)
    lateinit var txtFieldPassword: TextInputLayout

    val sessionManager = inventarios.uv.mx.apphospital.model.managers.SessionManager()

    var username: String? = null
    var password: String? = null

    val USERNAME = "USERNAME"
    val PASSWORD = "PASSWORD"


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(USERNAME, txtFieldUsername?.editText?.text.toString())
        outState.putString(PASSWORD, txtFieldPassword?.editText?.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        username = savedInstanceState.getString(USERNAME)
        password = savedInstanceState.getString(PASSWORD)
    }

    override fun setupListeners() {
        super.setupListeners()
        txtFieldPassword.editText?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                hideKeyboard()
                login()
            }
            false
        }
    }

    override fun loadContentExtension(firstLoading: Boolean) {
        showContentUi()
    }

    override fun showContentExtension() {
        username?.let { txtFieldUsername.editText?.setText(it) }
        password?.let { txtFieldPassword.editText?.setText(it) }
    }

    @OnClick(R.id.buttonLogin)
    fun btnLoginOnClick() {
        login()
    }

    private fun login() {
        ui.launch {
            var validateFields = validateFields()
            var login = Responses.CONNECTION_ERROR

            //var isToken = isToken(txtFieldUsername.editText?.text.toString())
            /*if (isToken) {
                isStudent = true
            } else {

                bg.async {
                    isStudent = isStudent(txtFieldUsername.editText?.text.toString())
                }.await()
            }*/

            if (!validateFields) {
                txtFieldUsername.visibility = View.GONE
                txtFieldPassword.visibility = View.GONE
                buttonLogin.visibility = View.GONE

                viewLoading.visibility = View.VISIBLE

                bg.async {
                    var user = User()
                    user.username = txtFieldUsername.editText?.text.toString()
                    user.password = txtFieldPassword.editText?.text.toString()

                    //if (isStudent) {
                    user.username = user.username?.toLowerCase()
                    //user.userType = C.UserType.STUDENT
                    /*} else {
                        user.username = user.username?.toLowerCase()?.trim()?.replace("@uv.mx", "")
                        user.userType = C.UserType.EMPLOYEE
                    }*/

                    //.................................login = sessionManager.login(user)
                    login = Responses.SUCCESS
                }.await()

                when (login){
                    Responses.CONNECTION_ERROR  ->{ activity?.let { activity ->
                        view?.let { view -> Snackbar.make(view, R.string.txt_connection_error, Snackbar.LENGTH_SHORT).show() }

                    }}
                    Responses.SUCCESS           -> presenter.login()
                    Responses.WRONG_DATA        -> { activity?.let { activity ->
                        view?.let { view -> Snackbar.make(view, R.string.txt_invalid_user_or_password, Snackbar.LENGTH_SHORT).show() }

                    }}
                    Responses.DATA_ERROR        ->{ activity?.let { activity ->
                        view?.let { view -> Snackbar.make(view, R.string.txt_data_error, Snackbar.LENGTH_SHORT).show() }

                    }}
                }


            }


            if (isAttached) {
                txtFieldUsername.visibility = View.VISIBLE
                txtFieldPassword.visibility = View.VISIBLE
                buttonLogin.visibility = View.VISIBLE

                viewLoading.visibility = View.GONE
            }
        }
    }


    private fun validateFields(): Boolean {

        /*if (isToken(txtFieldUsername.editText?.text.toString())) {
            return true
        }*/

        if (!validateField(txtFieldUsername)) {
            try {
                this.view?.let { Snackbar.make(it, R.string.txt_required_username, Snackbar.LENGTH_SHORT).show() }
                //editUsername.isErrorEnabled = true
                //editUsername.editText?.error = activity?.getString(R.string.txt_username_error)
            } catch (ex: Exception) {
                LogUtils.Error(debugTag, ex)
            }
            return false
        }

        if (!validateField(txtFieldPassword)) {
            try {
                this.view?.let { Snackbar.make(it, R.string.txt_required_password, Snackbar.LENGTH_SHORT).show() }
                //editPassword.isErrorEnabled = true
                //editPassword.editText?.error = activity?.getString(R.string.txt_password_error)
            } catch (ex: Exception) {
                LogUtils.Error(debugTag, ex)
            }
            return false
        }

        return true
    }

    /*private fun isToken(username: String): Boolean {
        val possibleToken = username
        if (possibleToken.count() > 180 && possibleToken.endsWith("=")) {
            return true
        }
        return false
    }*/

    private fun validateField(textInputLayout: TextInputLayout?): Boolean =
            !textInputLayout?.editText?.text.toString().isEmpty()

    private fun hideKeyboard() {
        activity?.let {
            val inputMethodManager: InputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity?.currentFocus
            if (view == null) {
                view = View(activity)
            }
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
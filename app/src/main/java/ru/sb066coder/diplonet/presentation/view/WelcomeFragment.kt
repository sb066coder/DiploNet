package ru.sb066coder.diplonet.presentation.view

import android.app.Activity
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import ru.sb066coder.diplonet.R
import ru.sb066coder.diplonet.databinding.FragmentWelcomeBinding
import ru.sb066coder.diplonet.presentation.util.AndroidUtils
import ru.sb066coder.diplonet.presentation.util.EmptyFieldErrorCanceler
import ru.sb066coder.diplonet.presentation.util.ViewUtil.toPx
import ru.sb066coder.diplonet.presentation.viewmodel.AuthViewModel

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private val viewModel: AuthViewModel by viewModels(::requireParentFragment)

    private var mode: Mode = Mode.SIGN_IN_MODE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            when (mode) {
                Mode.SIGN_IN_MODE -> {
                    binding.grSignUp.visibility = View.VISIBLE
                    mode = Mode.SIGN_UP_MODE
                }
                Mode.SIGN_UP_MODE -> {
                    viewModel.signUp(
                        binding.etName.text.toString(),
                        binding.etLogin.text.toString(),
                        binding.etPassword.text.toString(),
                        binding.etConfirmPassword.text.toString()
                    )
                }
            }
        }
        binding.btnSignIn.setOnClickListener {
            when (mode) {
                Mode.SIGN_IN_MODE -> {
                    viewModel.signIn(
                        binding.etLogin.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    AndroidUtils.hideKeyboard(requireView())
                }
                Mode.SIGN_UP_MODE -> {
                    binding.grSignUp.visibility = View.GONE
                    mode = Mode.SIGN_IN_MODE
                }
            }
        }
        binding.etName.addTextChangedListener(EmptyFieldErrorCanceler(
            NAME_FIELD_NAME, viewModel::cancelInputError
        ))
        binding.etLogin.addTextChangedListener(EmptyFieldErrorCanceler(
            LOGIN_FIELD_NAME, viewModel::cancelInputError
        ))
        binding.etPassword.addTextChangedListener(EmptyFieldErrorCanceler(
            PASSWORD_FIELD_NAME, viewModel::cancelInputError
        ))
        binding.etConfirmPassword.addTextChangedListener(EmptyFieldErrorCanceler(
            CONFIRM_PASSWORD_FIELD_NAME, viewModel::cancelInputError
        ))
        viewModel.authDone.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.authCheckFragment)
        }
        viewModel.errorName.observe(viewLifecycleOwner) {
            binding.tilName.error = if (it) getString(R.string.error_name) else null
        }
        viewModel.errorLogin.observe(viewLifecycleOwner) {
            binding.tilLogin.error = if (it) getString(R.string.error_login) else null
        }
        viewModel.errorPassword.observe(viewLifecycleOwner) {
            binding.tilPassword.error = if (it) getString(R.string.error_password) else null
        }
        viewModel.errorConfirmPassword.observe(viewLifecycleOwner) {
            binding.tilConfirmPassword.error = if (it) {
                getString(R.string.confirmation_error_message)
            } else null
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        val pickPhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when (it.resultCode) {
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(
                            requireActivity(), ImagePicker.getError(it.data), Toast.LENGTH_SHORT
                        ).show()
                    }
                    Activity.RESULT_OK -> {
                        val uri: Uri? = it.data?.data
                        viewModel.changePhoto(uri, uri?.toFile())
                    }
                    Activity.RESULT_CANCELED -> {
                        viewModel.changePhoto(null, null)
                    }
                }
            }
        binding.ivAvatar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(256)
                .provider(ImageProvider.GALLERY)
                .createIntent(pickPhotoLauncher::launch)
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            if (it.uri == null) {
                with(binding.ivAvatar) {
                    setImageResource(R.drawable.baseline_add_24)
                    setPadding(48.toPx())
                    imageTintList = ColorStateList.valueOf(resources.getColor(R.color.indigo_700))
                }
            } else {
                with(binding.ivAvatar) {
                    setImageURI(it.uri)
                    setPadding(0)
                    imageTintList = null
                }
            }
        }

    }

    companion object {
        enum class Mode {
            SIGN_IN_MODE, SIGN_UP_MODE
        }
        const val NAME_FIELD_NAME = "etName"
        const val LOGIN_FIELD_NAME = "etLogin"
        const val PASSWORD_FIELD_NAME = "etPassword"
        const val CONFIRM_PASSWORD_FIELD_NAME = "etConfirmPassword"
}


}

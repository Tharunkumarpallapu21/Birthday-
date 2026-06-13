package com.sixyears.onestory.ui.permission

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sixyears.onestory.R
import com.sixyears.onestory.databinding.FragmentPermissionBinding
import com.sixyears.onestory.notification.NotificationScheduler
import com.sixyears.onestory.util.PrefsHelper

class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        PrefsHelper.setNotificationsGranted(requireContext(), granted)
        if (granted) {
            NotificationScheduler.scheduleAll(requireContext())
            proceed()
        } else {
            showSettingsDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAllowNotifications.setOnClickListener {
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val alreadyGranted = ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

            if (alreadyGranted) {
                PrefsHelper.setNotificationsGranted(requireContext(), true)
                NotificationScheduler.scheduleAll(requireContext())
                proceed()
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // Below Android 13, notification permission is granted by default.
            PrefsHelper.setNotificationsGranted(requireContext(), true)
            NotificationScheduler.scheduleAll(requireContext())
            proceed()
        }
    }

    private fun showSettingsDialog() {
        if (!isAdded) return
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.notif_denied_title))
            .setMessage(getString(R.string.notif_denied_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.open_settings)) { _, _ ->
                openAppSettings()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                // Even if cancelled, allow the user to proceed into the app.
                proceed()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(intent)
    }

    private fun proceed() {
        PrefsHelper.setPermissionScreenShown(requireContext(), true)
        if (isAdded) {
            findNavController().navigate(PermissionFragmentDirections.actionPermissionToMain())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

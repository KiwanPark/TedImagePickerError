package com.sg.tedimagepickertest

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class IntroActivity : AppCompatActivity() {
    private var isShownDialog = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        getPermission()
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (isShownDialog) {
                getPermission()
            }
        }

    private fun getPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.deniedPermissionResponses != null && p0.deniedPermissionResponses.size != 0) {
                        var debugString = ""
                        for (data in p0.deniedPermissionResponses) {
                            debugString += data.permissionName + "\n"
                        }
                        reCheckPermission()
                    } else {
                        goNextActivity()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    if (p0 != null && p0.size != 0) {
                        reCheckPermission()
                    } else {
                        goNextActivity()
                    }
                }
            }).check()
    }

    private fun reCheckPermission() {
        isShownDialog = true
        val builder = AlertDialog.Builder(this)
            .setMessage("필수 권한에 동의하지 않으시면 앱 이용에 제한이 있을 수 있습니다. 계속하시겠습니까?")
            .setPositiveButton("동의") { _, _ ->
                goSetting()
            }
            .setNegativeButton("동의하지않기") { _, _ ->
                goNextActivity()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun goSetting() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:$packageName"))
        resultLauncher.launch(intent)
    }

    fun goNextActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
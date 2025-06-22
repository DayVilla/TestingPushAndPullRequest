import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Utility for picking PDF or image files from device storage while handling
 * Android storage permissions across different API levels.
 */
object FilePicker {
    const val REQUEST_OPEN_PDF = 1000
    const val REQUEST_OPEN_IMAGE = 2000
    const val REQUEST_STORAGE_PERMISSION = 3000

    /** Opens a system picker for selecting a PDF document. */
    fun openPdf(activity: Activity) {
        open(activity, "application/pdf", REQUEST_OPEN_PDF)
    }

    /** Opens a system picker for selecting an image. */
    fun openImage(activity: Activity) {
        open(activity, "image/*", REQUEST_OPEN_IMAGE)
    }

    /**
     * Internal helper that chooses the appropriate intent based on the
     * Android version and ensures permission checks for legacy devices.
     */
    private fun open(activity: Activity, mimeType: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = mimeType
            }
            activity.startActivityForResult(intent, requestCode)
        } else {
            if (checkAndRequestPermission(activity)) {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = mimeType
                }
                activity.startActivityForResult(intent, requestCode)
            }
        }
    }

    /**
     * Requests READ_EXTERNAL_STORAGE permission on versions where it is
     * required, returning true if permission is already granted.
     */
    private fun checkAndRequestPermission(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    REQUEST_STORAGE_PERMISSION
                )
                return false
            }
        }
        return true
    }
}

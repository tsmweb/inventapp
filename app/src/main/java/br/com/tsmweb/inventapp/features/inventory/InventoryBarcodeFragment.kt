package br.com.tsmweb.inventapp.features.inventory

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import br.com.tsmweb.inventapp.R
import br.com.tsmweb.inventapp.common.BaseFragment
import br.com.tsmweb.inventapp.common.Constants.EXTRA_BARCODE
import br.com.tsmweb.inventapp.common.Constants.EXTRA_INVENTORY
import br.com.tsmweb.inventapp.features.inventory.binding.InventoryBinding
import br.com.tsmweb.inventapp.features.inventory.camera.BarcodeImageAnalyzer
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.android.synthetic.main.fragment_inventory_barcode.*
import kotlinx.android.synthetic.main.top_action_bar_in_live_camera.*
import java.lang.Exception
import java.lang.Math.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class InventoryBarcodeFragment : BaseFragment() {

    private val TAG = InventoryBarcodeFragment::class.simpleName

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var preview: Preview? = null
    private var camera: Camera? = null

    private lateinit var cameraExecutor: ExecutorService

    private val inventory: InventoryBinding? by lazy {
        arguments?.getParcelable(EXTRA_INVENTORY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inventory_barcode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isCameraPermissionGranted()) {
            pvBarcode.post {
                startCamera()
            }
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }

        btnClose.setOnClickListener {
            router.back()
        }

        btnFlash.setOnClickListener {
            if (camera?.cameraInfo?.torchState?.value == TorchState.OFF) {
                it.isSelected = true
                camera?.cameraControl?.enableTorch(true)
            } else {
                it.isSelected = false
                camera?.cameraControl?.enableTorch(false)
            }
        }

        setFragmentResultListener(InventoryBarcodeInputFragment.BARCODE_INPUT_REQUEST_KEY) { key, bundle ->
            bundle.getString(EXTRA_BARCODE)?.let {
                onShowInventoryPatrimonyInfo(it)
            }
        }

        chipTypeBarcode.setOnClickListener {
            InventoryBarcodeInputFragment
                .newInstance()
                .open(parentFragmentManager)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStop() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onStop()
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val metrics = DisplayMetrics().also {
            pvBarcode.display.getRealMetrics(it)
        }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        val rotation = pvBarcode.display.rotation

        preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetRotation(rotation)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor, BarcodeImageAnalyzer { barcodes, next ->
            barcodes.forEach { barcode ->
                Log.i(TAG, "Barcode detected: ${barcode.rawValue}.")

                barcode.rawValue?.let {
                    onShowInventoryPatrimonyInfo(it)
                }
            }

//            next()
        })

        try {
            cameraProvider.unbindAll()

            camera = cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageAnalysis)
            preview?.setSurfaceProvider(pvBarcode.surfaceProvider)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)

        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }

        return AspectRatio.RATIO_16_9
    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                pvBarcode.post {
                    startCamera()
                }
            } else {
                Toast.makeText(requireContext(), "Camera permission is required.", Toast.LENGTH_SHORT).show()
                router.back()
            }
        }
    }

    private fun onShowInventoryPatrimonyInfo(barcode: String) {
        inventory?.let {
            InventoryPatrimonyInfoFragment
                .newInstance(
                    inventoryId = it.id,
                    barcode = barcode,
                    editMode = false
                )
                .open(parentFragmentManager)
        }
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

}
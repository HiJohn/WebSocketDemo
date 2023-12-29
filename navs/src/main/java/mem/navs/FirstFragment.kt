package mem.navs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import me.utils.Logs
import me.utils.registerPickVisualMedia
import me.utils.toast
import mem.navs.databinding.FragmentFirstBinding


/**
 * nav`s first page
 */
class FirstFragment : Fragment() {


    private lateinit var binding: FragmentFirstBinding

    private val viewModel: NavViewModel by navSharedLazyVm()
    private val takePhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {

        }




    private val mediaPicker = registerPickVisualMedia {
        if (it!=null){
            toast(" get uri :${it.toString()}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sjn.setOnClickListener {
            navTo2()
        }
        binding.cda.setOnClickListener {
            viewModel.netName.postValue("Not a name")
        }

        viewModel.netName.observe(requireActivity()){
            binding.show.text = it
        }


        binding.pick.setOnClickListener {
            if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()){
                mediaPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

            }else{
                toast(" picker unavailable")
            }

        }



    }

    private fun navTo2(){
        val args  = Bundle()
        args.putBoolean("testBool",true)
        args.putString("testStr","sayHey")
        nav().navigate(R.id.to_page_2,args)
    }

    private fun chooseMediaApi() {
        // Registers a photo picker activity launcher in single-select mode.
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Logs.logI("PhotoPicker", "Selected URI: $uri")
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        // Include only one of the following calls to launch(), depending on the types
        // of media that you want to let the user choose from.

        // Launch the photo picker and let the user choose images and videos.
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        // Launch the photo picker and let the user choose only videos.
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))

        // Launch the photo picker and let the user choose only images/videos of a
        // specific MIME type, such as GIFs.
        val mimeType = "image/gif"
        pickMedia.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.SingleMimeType(
                    mimeType
                )
            )
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() = FirstFragment()

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
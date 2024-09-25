package mem.navs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.utils.launchActivity
import mem.navs.databinding.FragmentThirdBinding

/**
 * nav`s third page
 *  in this page , trigger a button action back to first page
 */
class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    private val viewModel: NavViewModel by navSharedLazyVm()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bko.setOnClickListener {
            viewModel.netName.postValue("I`m BackOne")

            nav().popBackStack(R.id.firstFragment, false)

        }

        binding.startDrawer.setOnClickListener {
            launchActivity<DrawerNavActivity>()
        }

        binding.show3.text = viewModel.netName.value


    }

}
package com.exam.myapp

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.exam.myapp.databinding.ActivityEmptyBinding
import me.jscall.WebViewActivity
import me.utils.launchActivity
import me.utils.launchSd
import me.utils.registerPermLauncher
import me.utils.setClickableSpan
import me.utils.toast
import mem.navs.NavMainActivity

class EmptyActivity : AppCompatActivity() {

    private val binding: ActivityEmptyBinding by lazy {
        ActivityEmptyBinding.inflate(layoutInflater)
    }


    private val sdLauncher:ActivityResultLauncher<String> = registerPermLauncher {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.tackAction.setOnClickListener {
            action()

        }

        binding.tvDemo.setClickableSpan("如要获得称赞请点这里","请点这里",R.color.colorPrimary){
            toast("你真棒~")
        }



    }

    private fun showDf(){

        val dialogFragment = MeDialogFragment()
        dialogFragment.show(supportFragmentManager,"me")
    }

    private fun sdReq(){
        sdLauncher.launchSd()
    }

    private fun action(){
        launchActivity<NavMainActivity>()
//        launchActivity<WebViewActivity>()
    }

}
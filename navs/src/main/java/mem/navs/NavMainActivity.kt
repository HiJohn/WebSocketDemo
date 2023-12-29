package mem.navs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

class NavMainActivity : AppCompatActivity() {

//    private val viewModel: NavViewModel by lazy {
//        ViewModelProvider(this)[NavViewModel::class.java]
//    }

    private val viewModel : NavViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_main)
    }
}
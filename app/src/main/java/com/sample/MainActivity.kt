package com.sample

import android.os.Bundle
import android.util.Log.println
import androidx.fragment.app.FragmentActivity
import com.shengxiangui.cn.R


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dm = resources.displayMetrics

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.containerView, Video_BaseFragment.newInstance(Video.ORANGE_13.url))
                .commit()

        }


    }


}

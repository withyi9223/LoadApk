package com.cj.loadapk

import android.os.Bundle
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity() {

    lateinit var dialog: UpdateDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_down_load.setOnClickListener {
            AppUpdateUtil.getInstance().getiNetManager().get(this, object : INetCallback {
                override fun success(bean: UpdateBean) {
                    if (BuildConfig.VERSION_CODE < bean.data.currentVersionNo.toInt()) {
                        dialog = UpdateDialog.newInstance(bean)
                        dialog.show(supportFragmentManager, "update")
                    }
                }

                override fun failed(throwable: Throwable?) {
                }

            })

        }

    }

}

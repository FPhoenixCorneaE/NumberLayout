package com.fphoenixcorneae.numberlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.fphoenixcorneae.numberlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mDataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mDataBinding.apply {
            flNumberKeyboard.onClickListener = {
                Toast.makeText(this@MainActivity, it.joinToString(), Toast.LENGTH_SHORT).show()
                llNumberDisplay.correctNumbers = "0305"
                llNumberDisplay.onFillFinishedListener = {
                    Toast.makeText(
                        this@MainActivity,
                        if (it) {
                            "验证成功！"
                        } else {
                            "验证失败！"
                        },
                        Toast.LENGTH_SHORT
                    ).show()
                }
                llNumberDisplay.fillNumbers(it)
            }
        }
    }
}
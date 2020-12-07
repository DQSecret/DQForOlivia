package com.example.dqddu.motionlayout.example

import com.example.dqddu.base.BaseBindingActivity
import com.example.dqddu.databinding.ViewSwipeMotionLayoutBinding

/**
 * MotionLayout示例代码
 *
 * @author DQDana For Olivia
 * @since 2020/12/7 10:57 AM
 * @see <a href="文章">https://proandroiddev.com/building-swipeview-using-motionlayout-7a80fd06401c</a>
 */
class MotionLayoutExampleActivity : BaseBindingActivity<ViewSwipeMotionLayoutBinding>() {

    override fun initBinding() = ViewSwipeMotionLayoutBinding.inflate(layoutInflater)
}
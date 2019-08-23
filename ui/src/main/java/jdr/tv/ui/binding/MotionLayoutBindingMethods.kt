package jdr.tv.ui.binding

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(
    value = [BindingMethod(
        type = MotionLayout::class,
        attribute = "motionProgress",
        method = "setProgress"
    )]
)
class MotionLayoutBindingMethods

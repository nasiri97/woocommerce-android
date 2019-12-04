package com.woocommerce.android.ui.products

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.woocommerce.android.R
import com.woocommerce.android.util.WooLog

/**
 * Used by product detail to show product property name & value, with an optional ratingBar
 */
class WCProductPropertyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private var view: View? = null
    private var propertyGroupImg: ImageView? = null
    private var propertyNameText: TextView? = null
    private var propertyValueText: TextView? = null
    private var ratingBar: RatingBar? = null

    fun show(orientation: Int, caption: String, detail: CharSequence?) {
        ensureViewCreated(orientation)

        propertyNameText?.text = caption
        if (detail.isNullOrEmpty()) {
            propertyValueText?.visibility = View.GONE
        } else {
            propertyValueText?.text = detail
        }
    }

    /**
     * Adds a click listener to the property view
     */
    fun setClickListener(onClickListener: ((view: View) -> Unit)? = null) {
        onClickListener?.let {
            propertyGroupImg?.visibility = View.VISIBLE
            view?.setOnClickListener(onClickListener)
        }
    }

    fun setMaxLines(maxLines: Int) {
        propertyValueText?.maxLines = maxLines
    }

    fun setRating(rating: Float) {
        ensureViewCreated()

        try {
            ratingBar?.rating = rating
            ratingBar?.visibility = View.VISIBLE
        } catch (e: NumberFormatException) {
            WooLog.e(WooLog.T.UTILS, e)
        }
    }

    private fun ensureViewCreated(orientation: Int = LinearLayout.VERTICAL) {
        if (view == null) {
            view = if (orientation == LinearLayout.VERTICAL) {
                View.inflate(context, R.layout.product_property_view_vert, this)
            } else {
                View.inflate(context, R.layout.product_property_view_horz, this)
            }
            propertyGroupImg = view?.findViewById(R.id.imgProperty)
            propertyNameText = view?.findViewById(R.id.textPropertyName)
            propertyValueText = view?.findViewById(R.id.textPropertyValue)
            ratingBar = view?.findViewById(R.id.ratingBar)
        }
    }
}

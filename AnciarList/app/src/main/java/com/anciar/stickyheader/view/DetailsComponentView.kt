package com.anciar.stickyheader.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.anciar.stickyheader.R
import com.anciar.stickyheader.data.SectionPojo
import com.anciar.stickyheader.databinding.DetailsComponentBinding
import com.anciar.stickyheader.databinding.DetailsListItemBinding
import com.anciar.stickyheader.extensions.requestLayoutForChangedDataset

class DetailsComponentView : ConstraintLayout {

    private lateinit var binding: DetailsComponentBinding
    private lateinit var detailsListItemBinding: DetailsListItemBinding
    private lateinit var adapter: StickyHeaderAdapter

    var content: List<SectionPojo> = emptyList()
        set(value) {
            field = value
            onItemsUpdated()
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        binding = DetailsComponentBinding.inflate(LayoutInflater.from(context), this, true)
        adapter = StickyHeaderAdapter(context)
        binding.itemDetailsList.adapter = adapter
    }

    private fun onItemsUpdated() {
        adapter.notifyDataSetChanged()
        binding.itemDetailsList.requestLayoutForChangedDataset()
    }

    inner class StickyHeaderAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val sectionPojo: SectionPojo = content[position]
            var view: View? = convertView

            if (view == null) {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.details_list_item, parent, false)
                detailsListItemBinding = DetailsListItemBinding.bind(view)
                view.tag = detailsListItemBinding
            } else {
                detailsListItemBinding = view.tag as DetailsListItemBinding
            }

            return detailsListItemBinding.root
        }

        override fun getItem(position: Int): Any {
            return content[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return content.size
        }

        override fun isEnabled(position: Int): Boolean {
            return false
        }
    }
}
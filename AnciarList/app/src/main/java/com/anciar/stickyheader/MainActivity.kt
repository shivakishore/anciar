package com.anciar.stickyheader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anciar.stickyheader.data.SectionPojo
import com.anciar.stickyheader.databinding.ActivityMainBinding
import com.anciar.stickyheader.databinding.ViewListItemBinding
import com.anciar.stickyheader.extensions.setDivider
import com.anciar.stickyheader.utils.JsonUtils
import com.anciar.stickyheader.utils.StickyHeaderDecoration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sectionedListAdapter = SectionedListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val sectionPojos: List<SectionPojo> = JsonUtils.getItems(this)
        val groupedItems: Map<String, List<SectionPojo>> =
            sectionPojos.groupBy { item -> "Section ${item.title.first().toUpperCase()}" }
        sectionedListAdapter.sectionPojoData = groupedItems.toSortedMap()

        binding.itemList.setDivider(R.drawable.list_divider)
        binding.itemList.addItemDecoration(
            StickyHeaderDecoration(sectionedListAdapter, binding.root)
        )
        binding.itemList.layoutManager = LinearLayoutManager(this)
        binding.itemList.adapter = sectionedListAdapter
    }

    class SectionedListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var itemHeaders: List<String> = listOf()

        var sectionPojoData: Map<String, List<SectionPojo>> = emptyMap()
            set(value) {
                field = value
                itemHeaders = sectionPojoData.keys.toList()
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
            val viewBinding: ViewListItemBinding =
                ViewListItemBinding.inflate(layoutInflater, parent, false)
            return ItemViewHolder(viewBinding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (position >= 0 && position < itemHeaders.size) {
                (holder as ItemViewHolder).bind(itemHeaders[position])
            }
        }

        override fun getItemCount() = itemHeaders.size

        fun getHeaderForCurrentPosition(position: Int) = if (position in itemHeaders.indices) {
            itemHeaders[position]
        } else {
            ""
        }

        inner class ItemViewHolder(private val viewBinding: ViewListItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

            fun bind(header: String) {
                viewBinding.tvHeader.text = header
                sectionPojoData[header]?.let { items ->
                    viewBinding.itemDetailsView.content = items
                }
            }
        }
    }
}
package com.indialone.recyclerviewitemselection

import android.app.Activity
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(
    private val activity: Activity,
    private val list: ArrayList<ListItem>
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var listViewModel: ListViewModel
    private var isEnable: Boolean = false
    private var isSelectAll: Boolean = false
    private var selectedItems = ArrayList<ListItem>()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv = itemView.findViewById<ImageView>(R.id.iv)
        val tv = itemView.findViewById<TextView>(R.id.tv)
        val ivCheckBox = itemView.findViewById<ImageView>(R.id.iv_checked)

        fun bind(item: ListItem) {
            tv.text = item.title
            Glide.with(itemView.context)
                .load(item.image)
                .centerCrop()
                .into(iv)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)

        listViewModel =
            ViewModelProvider(
                activity as FragmentActivity,
                ViewModelFactory()
            ).get(ListViewModel::class.java)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                if (!isEnable) {
                    val callback = object : ActionMode.Callback {
                        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                            val menuInflater: MenuInflater = mode!!.menuInflater
                            menuInflater.inflate(R.menu.menu, menu)

                            return true
                        }

                        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                            isEnable = true

                            clickItem(holder)

                            listViewModel.getText().observe(activity as FragmentActivity) {
                                mode?.title = "Selected $it"
                            }

                            return true
                        }

                        override fun onActionItemClicked(
                            mode: ActionMode?,
                            item: MenuItem?
                        ): Boolean {

                            when (item?.itemId) {
                                R.id.menu_delete -> {
                                    for (listItem in selectedItems) {
                                        list.remove(listItem)
                                    }
                                    if (list.size == 0) {
                                        if (activity is MainActivity) {
                                            activity.visibleTvDataNotFound()
                                        }
                                    }
                                    mode?.finish()
                                }
                                R.id.menu_select_all -> {
                                    if (selectedItems.size == list.size) {
                                        isSelectAll = false
                                        selectedItems?.clear()
                                    } else {
                                        isSelectAll = true
                                        selectedItems.clear()

                                        selectedItems.addAll(list)
                                    }
                                    listViewModel.setText("${selectedItems.size}")
                                    notifyDataSetChanged()
                                }
                            }

                            return true
                        }

                        override fun onDestroyActionMode(mode: ActionMode?) {
                            isEnable = false
                            isSelectAll = false
                            selectedItems.clear()
                            notifyDataSetChanged()
                        }
                    }

                    ((v?.context) as AppCompatActivity).startActionMode(callback)

                } else {
                    clickItem(holder)
                }
                return true
            }
        })

        holder.itemView.setOnClickListener {
            if (isEnable) {
                clickItem(holder)
            } else {
                Toast.makeText(activity, "${holder.adapterPosition}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if (isSelectAll) {
            holder.ivCheckBox.visibility = View.VISIBLE
        } else {
            holder.ivCheckBox.visibility = View.GONE
        }

    }

    private fun clickItem(holder: MyViewHolder) {
        val listItem = list[holder.adapterPosition]

        if (holder.ivCheckBox.visibility == View.GONE) {
            holder.ivCheckBox.visibility = View.VISIBLE
            selectedItems.add(listItem)
        } else {
            holder.ivCheckBox.visibility = View.GONE
            selectedItems.remove(listItem)
        }
        listViewModel.setText("${selectedItems.size}")
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
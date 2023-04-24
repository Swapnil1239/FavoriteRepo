package com.example.favoriterepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RepoAdapter(var mRepoList: MutableList<RepoItem>?) : RecyclerView.Adapter<RepoAdapter.RepoItemViewHolder>() {
    private var selectedItemIndex = -1

    inner class RepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.title)
        val descriptionView = itemView.findViewById<TextView>(R.id.description)

        fun selectItem() {
            if (selectedItemIndex == -1) {
                itemView.isActivated = false
            } else {
                if (selectedItemIndex == adapterPosition) {
                    itemView.isSelected = true
                    notifyItemChanged(selectedItemIndex)
                } else {
                    itemView.isActivated = false
                }
            }
            itemView.setOnClickListener {
                android.util.Log.d("zzzz","onItemClicked")
                itemView.isActivated = true
                if (selectedItemIndex != adapterPosition) {
                    notifyItemChanged(selectedItemIndex)
                    selectedItemIndex = adapterPosition
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.RepoItemViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val rowItemView = inflater.inflate(R.layout.row_item, parent, false)
        return RepoItemViewHolder(rowItemView)
    }
    override fun getItemCount(): Int {
        return mRepoList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        val repoData = mRepoList?.get(position)
        holder.titleView.text = repoData?.mRepoName ?: ""
        holder.descriptionView.text = repoData?.mRepoDescription ?: ""
        holder.selectItem()
        android.util.Log.d("zzzz","onBindViewHolder")
    }

}
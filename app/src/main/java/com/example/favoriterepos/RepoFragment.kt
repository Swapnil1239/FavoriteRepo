package com.example.favoriterepos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RepoFragment : Fragment() {
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var addButton: ImageView
    private lateinit var repoAdapter: RepoAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.repo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoViewModel = RepoViewModel()
        context?.let { repoViewModel.loadList(it) }
        addButton = view.findViewById(R.id.add_button)
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.saved_repo_list)
//        for (i in 1..100) {
//            val repoItem = RepoItem("xyz","hgoehgoerjglerkgdrggrpighr",
//            "www.google.co.in")
//            repoList.add(repoItem)
//        }
        repoAdapter = RepoAdapter(repoViewModel.repoList.value)
        var itemDecorator = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
        view.context.getDrawable(R.drawable.item_divider)?.let { itemDecorator.setDrawable(it) }
        recyclerView.apply {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(itemDecorator)
        }
        repoViewModel.repoList.observe(viewLifecycleOwner){newList ->
            repoAdapter.mRepoList = newList
            repoAdapter.notifyDataSetChanged()
            android.util.Log.d("zzzz","repoList: ${newList.size}")
        }
        addButton.setOnClickListener {
            showRepoInfoFragment()
        }
    }

    private fun showRepoInfoFragment() {
        with(childFragmentManager) {
            beginTransaction().replace(
                R.id.repo_info_fragment_container, RepoInfoFragment(),
                REPO_INFO_FRAGMENT_TAG).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        repoAdapter.notifyDataSetChanged()
    }
    companion object{
        private val REPO_INFO_FRAGMENT_TAG="RepoInfoFragment"
    }
}
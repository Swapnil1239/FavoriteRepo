package com.example.favoriterepos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.findFragment

class RepoInfoFragment: Fragment() {
    private lateinit var backButton: ImageView
    private lateinit var addButton: ImageView
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var owner: EditText
    private lateinit var repoName: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repo_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoViewModel = RepoViewModel()
        backButton = view.findViewById(R.id.back_button)
        backButton.setOnClickListener {
            this.view?.visibility = View.GONE
        }
        addButton = view.findViewById(R.id.add_repo_button)
        owner = view.findViewById(R.id.repository_owner)
        repoName = view.findViewById(R.id.reposistory_name)
        addButton.setOnClickListener{
            context?.let { it1 -> repoViewModel.addRepo(it1, owner.text.toString(), repoName.text.toString()) }
            android.util.Log.d("zzzz","add button")
        }
    }

    companion object {
        private const val REPO_FRAGMENT_TAG = "RepoFragment"
    }
}
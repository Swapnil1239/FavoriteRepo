package com.example.favoriterepos

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SplashFragment : Fragment() {
    private lateinit var animatedImage: ImageView
    private var anim: ValueAnimator? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                           savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.splash_fragment, container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animatedImage = requireView().findViewById(R.id.welcome_msg)
        anim = ValueAnimator.ofFloat(0f, 1f)
        anim?.duration = 1500
        anim?.addUpdateListener { animation ->
            animatedImage.alpha = animation?.animatedValue as Float
        }
        anim?.repeatCount = 1
        anim?.start()

        Executors.newSingleThreadScheduledExecutor().schedule({
            showRepoFragment()
        },5, TimeUnit.SECONDS)
    }

    private fun showRepoFragment() {
        childFragmentManager.beginTransaction().replace(
            R.id.repo_fragment,
            RepoFragment(), REPO_FRAGMENT_TAG
        ).addToBackStack(null).commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        anim?.cancel()
    }

    companion object {
        private const val REPO_FRAGMENT_TAG = "RepoFragment"
    }
}
package com.kevcordova.jobsitychallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.databinding.FragmentEpisodeInfoFullscreenBinding
import com.kevcordova.jobsitychallenge.ui.fragment.presenter.EpisodeViewModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class EpisodeInfoFullscreenFragment : DialogFragment() {

    private val episodeSharedViewModel by lazy {
        EpisodeViewModel()
    }

    private var _binding: FragmentEpisodeInfoFullscreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_JobsityCallengeApp_Light_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeInfoFullscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarEpisodeInfo.setNavigationOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val widthHeightLayout = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(widthHeightLayout, widthHeightLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
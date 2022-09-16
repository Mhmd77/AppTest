package com.example.testproject.ui.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.ServiceLocator
import com.example.testproject.data.source.Repository
import com.example.testproject.databinding.FragmentHomeBinding
import com.example.testproject.utilities.AndroidUtilities
import com.example.testproject.utilities.Utilities
import com.google.android.material.bottomsheet.BottomSheetBehavior

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(ServiceLocator.provideRepository(), requireActivity().application)
    }

    private val adapter: TransactionAdapter by lazy {
        TransactionAdapter(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val behavior = BottomSheetBehavior.from(binding.bottomSheet)
        val headerLayout = binding.headerLayout
        (headerLayout.layoutParams as CoordinatorLayout.LayoutParams).topMargin =
            AndroidUtilities.statusBarHeight
        headerLayout.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                headerLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val screenHeight = AndroidUtilities.getScreenHeight(requireActivity())
                val heightSize =
                    headerLayout.height + AndroidUtilities.dp(16f) + (headerLayout.layoutParams as CoordinatorLayout.LayoutParams).topMargin
                behavior.peekHeight = screenHeight - heightSize
            }
        })
        binding.transactionsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomeFragment.adapter
        }
        binding.balanceTextView.text = Utilities.getPriceString("78500000")

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    shimmerLayout.visibility = View.VISIBLE
                    shimmerLayout.startShimmer()
                    transactionsList.visibility = View.GONE
                }
            } else {
                with(binding) {
                    shimmerLayout.visibility = View.GONE
                    shimmerLayout.stopShimmer()
                    transactionsList.visibility = View.VISIBLE
                }
            }
        }

        viewModel.transactionList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class HomeViewModelFactory(
    private val repository: Repository,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        HomeViewModel(application, repository) as T
}
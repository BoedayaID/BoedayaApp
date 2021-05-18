package com.boedayaid.boedayaapp.ui.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.boedayaid.boedayaapp.data.model.Suku
import com.boedayaid.boedayaapp.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        private const val PROVINCE_ID = "province_id"

        fun newInstance(id: String): BottomSheetFragment =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(PROVINCE_ID, id)
                }
            }
    }

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomSheetViewModel by viewModels()

    private lateinit var provinceId: String
    private lateinit var sukuAdapter: SukuAdapter
    private lateinit var listSuku : List<Suku>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        provinceId = arguments?.getString(PROVINCE_ID).toString()
        sukuAdapter = SukuAdapter()

        sukuAdapter.setOnClick { position ->
            Toast.makeText(context, listSuku[position].toString(), Toast.LENGTH_SHORT).show()
        }
        binding.rvGridSuku.apply {
            adapter = sukuAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }

        viewModel.getSukuFromServer(provinceId)
        viewModel.listSuku.observe(viewLifecycleOwner) { list ->
            listSuku = list
            sukuAdapter.setList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
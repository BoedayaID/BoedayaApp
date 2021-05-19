package com.boedayaid.boedayaapp.ui.home.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.boedayaid.boedayaapp.data.model.Suku
import com.boedayaid.boedayaapp.databinding.FragmentBottomSheetBinding
import com.boedayaid.boedayaapp.ui.detail.DetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        private const val PROVINCE_ID = "province_id"
        private const val PROVINCE_NAME = "province_name"

        fun newInstance(id: String, name: String): BottomSheetFragment =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(PROVINCE_ID, id)
                    putString(PROVINCE_NAME, name)
                }
            }
    }

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BottomSheetViewModel by viewModels()

    private lateinit var provinceId: String
    private lateinit var provinceName: String
    private lateinit var sukuAdapter: SukuAdapter
    private lateinit var listSuku: List<Suku>
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
        provinceName = arguments?.getString(PROVINCE_NAME).toString()

        binding.tvProvinceName.text = "Provinsi $provinceName"

        sukuAdapter = SukuAdapter()
        sukuAdapter.setOnClick { position ->
            Intent(context, DetailActivity::class.java).also {
                it.putExtra("SUKU_ID", listSuku[position].id)
                startActivity(it)
            }
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
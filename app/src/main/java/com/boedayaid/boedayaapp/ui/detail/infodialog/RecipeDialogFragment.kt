package com.boedayaid.boedayaapp.ui.detail.infodialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.FragmentRecipeDialogBinding
import com.boedayaid.boedayaapp.ui.recipe.RecipeActivity
import com.bumptech.glide.Glide

class RecipeDialogFragment : DialogFragment() {

    private var _binding: FragmentRecipeDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var makanan: DetailSuku.Makanan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makanan = arguments?.getParcelable(DATA)!!

        binding.apply {
            Glide.with(requireContext())
                .load(makanan.gambar)
                .into(imgPoster)
            tvTitle.text = makanan.namaMakanan
            tvDesc.text = makanan.deskripsi
        }

        binding.btnRecipe.setOnClickListener {
            Intent(requireContext(), RecipeActivity::class.java).also {
                it.putExtra("DATA", makanan)
                startActivity(it)
            }
        }
    }


    companion object {
        private const val DATA = "data"

        fun newInstance(data: DetailSuku.Makanan): RecipeDialogFragment =
            RecipeDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DATA, data)
                }
            }
    }
}
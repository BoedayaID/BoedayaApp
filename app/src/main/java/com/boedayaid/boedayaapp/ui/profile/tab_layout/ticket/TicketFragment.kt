package com.boedayaid.boedayaapp.ui.profile.tab_layout.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.boedayaid.boedayaapp.databinding.FragmentTicketBinding
import com.boedayaid.boedayaapp.ui.profile.tab_layout.TicketListAdapter
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TicketFragment : Fragment() {
    private var user: FirebaseUser? = null

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TicketViewModel by viewModels()
    private lateinit var ticketListAdapter: TicketListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = Firebase.auth.currentUser
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (user != null) {
            viewModel.getTicketList(user?.uid!!)
        }
        ticketListAdapter = TicketListAdapter()
        ticketListAdapter.setOnClick {

        }

        binding.list.apply {
            adapter = ticketListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        viewModel.resultList.observe(viewLifecycleOwner) { result ->
            if (result.isEmpty()) {
                binding.status.visibility = View.VISIBLE
                binding.loading.visibility = View.GONE
                binding.status.text = "Kosong"
            } else {
                binding.status.visibility = View.GONE
                binding.loading.visibility = View.GONE
                ticketListAdapter.setList(result)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
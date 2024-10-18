package com.example.parquepumalinapp.navBottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parquepumalinapp.databinding.FragmentMapaBinding

class MapaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMapaBinding.inflate(inflater, container, false)
        return binding.root
    }
}
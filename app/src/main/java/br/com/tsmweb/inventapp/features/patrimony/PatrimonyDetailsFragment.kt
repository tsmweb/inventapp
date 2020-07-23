package br.com.tsmweb.inventapp.features.patrimony

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.tsmweb.inventapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PatrimonyDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patrimony_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFab()
    }

    private fun initFab() {
        val fab = parentFragment?.view?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            Toast.makeText(requireContext(), "Detalhes Patrimônio", Toast.LENGTH_SHORT).show()
        }
    }

}
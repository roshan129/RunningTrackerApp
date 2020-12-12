package com.adivid.runningtrackerapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adivid.runningtrackerapp.R
import com.adivid.runningtrackerapp.other.Constants.KEY_NAME
import com.adivid.runningtrackerapp.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_setup.etName
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings){

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFieldsFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPrefs()
            if(success){
                Snackbar.make(view, "Saved Changes", Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(view, "Please fill out all the fields", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadFieldsFromSharedPref(){
        val name = sharedPreferences.getString(KEY_NAME, "")
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)
        etName.setText(name)
        etWeight.setText(weight.toString())
    }

    private  fun applyChangesToSharedPrefs(): Boolean{
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()
        if(nameText.isEmpty() || weightText.isEmpty()){

            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()
        val toolbarText = "Let's go $nameText"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }


}
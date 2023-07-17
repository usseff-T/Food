package com.example.food.ui.fragment.reciped

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.food.R
import com.example.food.databinding.FragmentBlankBottomSheetBinding
import com.example.food.databinding.FragmentRecipedBinding
import com.example.food.util.constants
import com.example.food.viewModel.recipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import java.util.Locale

class BlankBottomSheet : BottomSheetDialogFragment() {

    lateinit var recipesViewModel: recipesViewModel

    var mealType=constants.DEFAULT_MEAL_TYPE
    var mealTypeId=0
    var dietType=constants.DEFAULT_DIET_TYPE
    var dietTypeId=0

    private var _binding: FragmentBlankBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipesViewModel=ViewModelProvider(requireActivity()).get(recipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentBlankBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,{value->
            mealType=value.selectMealsType
            dietType=value.selectDietType
            updateChip(value.selectMealsTypeId, _binding!!.maelTypeChip)
            updateChip(value.selectDieTypeId, _binding!!.chipGroupeDiet)
        })
    binding.maelTypeChip.setOnCheckedChangeListener { group, checkedIds ->
        val chip=group.findViewById<Chip>(checkedIds)
        val selectMealType=chip.text.toString().toLowerCase(Locale.ROOT)
        mealType=selectMealType
        mealTypeId=checkedIds
    }

        binding.chipGroupeDiet.setOnCheckedChangeListener { group, checkedIds ->
            val chip=group.findViewById<Chip>(checkedIds)
            val selectDietType=chip.text.toString().toLowerCase(Locale.ROOT)
            dietType=selectDietType
            dietTypeId=checkedIds
        }
        binding.buttonSubmit.setOnClickListener {
recipesViewModel.saveMealAndDietType(
    mealType,
    mealTypeId,
    dietType,
    dietTypeId
)
        }

    return view
    }

    private fun updateChip(selectDieTypeId: Int, chipGroupeDiet: ChipGroup) {
        if(selectDieTypeId!=0){
            try{
                chipGroupeDiet.findViewById<Chip>(selectDieTypeId).isChecked=true
            }catch (e:Exception){
                Log.e("updateChip",""+e.message.toString())
            }
        }
    }

}
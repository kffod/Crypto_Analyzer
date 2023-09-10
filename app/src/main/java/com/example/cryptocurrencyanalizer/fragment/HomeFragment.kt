package com.example.cryptocurrencyanalizer.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.cryptocurrencyanalizer.R
import com.example.cryptocurrencyanalizer.adapter.TopLossGainPagerAdapter
import com.example.cryptocurrencyanalizer.adapter.TopMarketAdapter
import com.example.cryptocurrencyanalizer.apis.ApiInterface
import com.example.cryptocurrencyanalizer.apis.ApiUtilities
import com.example.cryptocurrencyanalizer.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        getTopCurrencylist()

        setTabLayout()


        return binding.root
    }

    private fun setTabLayout() {
        val adapter = TopLossGainPagerAdapter(this)
        binding.contentViewPager.adapter = adapter

        binding.contentViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 0){
                    binding.topGainIndicator.visibility = VISIBLE
                    binding.topLoseIndicator.visibility = GONE
                }else{
                    binding.topGainIndicator.visibility = GONE
                    binding.topLoseIndicator.visibility = VISIBLE
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.contentViewPager){
            tab, position ->
            var title = if(position == 0){
                "Top Gainers"
            }else{
                "Top Losers"
            }
            tab.text = title
        }.attach()
    }


    private fun getTopCurrencylist() {
        lifecycleScope.launch(Dispatchers.IO) {
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            withContext(Dispatchers.Main)
            {
                binding.topCurrencyRecyclerView.adapter =
                    TopMarketAdapter(requireContext(), res.body()!!.data.cryptoCurrencyList)
            }

            Log.d("SHUBH", "getTopCurrencyList: ${res.body()!!.data.cryptoCurrencyList}")
        }
    }
}



/*  private fun getTopCurrencylist() {
      lifecycleScope.launch(Dispatchers.IO) {
          try {
              val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)

              withContext()

              val response = apiInterface.getMarketData()

              if (response.isSuccessful) {
                  val marketData = response.body()
                  if (marketData != null) {
                      // Now you can work with the marketData
                      Log.d("SHUBH", "getTopCurrencyList: ${marketData.data.cryptoCurrencyList}")
                  } else {
                      Log.e("SHUBH", "Response body is null.")
                  }
              } else {
                  Log.e("SHUBH", "API call failed with code: ${response.code()}")
              }
          } catch (e: Exception) {
              Log.e("SHUBH", "Error: ${e.message}", e)
          }
      }
  }

}*/

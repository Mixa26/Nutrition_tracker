package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.adapter.ViewPageAdapter

class TabMainFragment : Fragment() {

    private lateinit var pager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var bar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_tab_main, container, false)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
    }

    private fun init(view: View){
        initTab(view)
    }

    private fun initTab(view: View){
        pager = view.findViewById(R.id.viewPager)
        tab = view.findViewById(R.id.tabs)
        bar = view.findViewById(R.id.toolbar)

        (requireActivity() as AppCompatActivity).setSupportActionBar(bar)

        val adapter = ViewPageAdapter(childFragmentManager)

        adapter.addFragment(TabCategoryFragment(), "Category")
        adapter.addFragment(TabAreaFragment(), "Area")
        adapter.addFragment(TabIngredientFragment(), "Ingredient")

        pager.adapter = adapter

        tab.setupWithViewPager(pager)

    }

}
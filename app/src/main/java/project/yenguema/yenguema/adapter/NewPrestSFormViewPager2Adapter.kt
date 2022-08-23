package project.yenguema.yenguema.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class NewPrestSFormViewPager2Adapter(
    listForm:Array<Fragment>,
    fm: FragmentManager,
    lc: Lifecycle):FragmentStateAdapter(fm, lc) {

    private val list = listForm
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
       return list[position]
    }
}
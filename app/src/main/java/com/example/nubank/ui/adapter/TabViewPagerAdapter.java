package com.example.nubank.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nubank.ui.fragment.AllInvoiceFragment;
import com.example.nubank.ui.fragment.CurrentInvoiceFragment;

import java.util.ArrayList;

public class TabViewPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public TabViewPagerAdapter( FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new CurrentInvoiceFragment());
        fragments.add(new AllInvoiceFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}

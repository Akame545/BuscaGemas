package com.alexmartin.buscagemas.tutorial;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.alexmartin.buscagemas.R;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment1;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment2;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment3;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment4;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment5;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment6;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment7;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment8;
import com.alexmartin.buscagemas.tutorial.fragments.Fragment9;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class TutorialActivity extends FragmentActivity {
    //Dicta el número de paginas
    private static final int NUM_PAGES = 9;
    //Pager widget, que otorga animacion y posibilidad de swipear lateralmente
    private ViewPager2 view_pager;
    //El adapter que provee las páginas al ViewPager
    private FragmentStateAdapter pagerAdapter;
    WormDotsIndicator dotsIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Instanciacion del ViewPager2 y PagerAdapter.
        view_pager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        view_pager.setAdapter(pagerAdapter);
        dotsIndicator = (WormDotsIndicator) findViewById(R.id.worm_dots_indicator);
        dotsIndicator.setViewPager2(view_pager);
    }

    //Un simple adaptador el cual representa NUM_PAGES objetos de pagina en secuencia.
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
//            getPosition(position);
            switch(position){
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment3();
                case 3:
                    return new Fragment4();

                case 4:
                    return new Fragment5();
                case 5:
                    return new Fragment6();
                case 6:
                    return new Fragment7();

                case 7:
                    return new Fragment8();

                case 8:
                    return new Fragment9();
                    /*
                case 9:
                    return new Fragment9();*/
            }
            return new Fragment1();
        }
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
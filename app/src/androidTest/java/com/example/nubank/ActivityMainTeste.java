package com.example.nubank;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import com.example.nubank.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class ActivityMainTeste {

    @Rule
    public ActivityScenarioRule<MainActivity> activity = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void iniciarComSaldoAberto() {
        onView(withId(R.id.tv_saldo_conta))
                .check(matches(withText(R.string.txt_balance)));
    }

    @Test
    public void esconderSaldo() {
        onView(withId(R.id.img_eyes)).perform(click());
        onView(withId(R.id.tv_saldo_conta))
                .check(matches(withText(R.string.txt_balance_hidden)));
    }

    @Test
    public void mostrarSaldo() {
        onView(withId(R.id.img_eyes)).perform(click());
        onView(withId(R.id.img_eyes)).perform(click());
        onView(withId(R.id.tv_saldo_conta))
                .check(matches(withText(R.string.txt_balance)));
    }
}


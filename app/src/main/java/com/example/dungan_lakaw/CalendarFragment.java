package com.example.dungan_lakaw;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dungan_lakaw.R;
import com.example.dungan_lakaw.cardview.CardViewAdapter;
import com.example.dungan_lakaw.cardview.EventCards;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private ImageButton btnleft, btnright;
    private RecyclerView recyclerView;
    private CardViewAdapter adapter;
    private ArrayList<EventCards> eventArrayList;
    private ImageButton btnadd;
    private ImageView imgview;

    private TextView currmonth;

    private ViewPager2 mViewPager;
    private int currImage = 1;

    private int mYear, mMonth, mDay;

    private ScrollView scrollView;

    private LinearLayout linearLayout;

    private EditText inputEditText;

    Calendar calendar = Calendar.getInstance();
    int currMonthno = calendar.get(Calendar.MONTH);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        btnleft = view.findViewById(R.id.btnleft);
        btnright = view.findViewById(R.id.btnright);
        btnadd = view.findViewById(R.id.btnaddnewevent);
        currmonth = view.findViewById(R.id.textView);

        initializeCardView(view);

        txtcurrmonth(currMonthno);

        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currMonthno -= 1;
                txtcurrmonth(currMonthno);
            }
        });

        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currMonthno += 1;
                txtcurrmonth(currMonthno);
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                // Store the selected date
                                mYear = year;
                                mMonth = month;
                                mDay = day;

                                openPopup(view);
                            }
                        },
                        year, currMonthno, dayOfMonth);
                datePickerDialog.show();
            }
        });

        return view;
    }

    private void txtcurrmonth(int currMonthno) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        currmonth.setText(monthNames[currMonthno]);
    }

    private void initializeCardView(View view) {
        recyclerView = view.findViewById(R.id.Calrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        eventArrayList = new ArrayList<>();

        adapter = new CardViewAdapter(requireContext(), eventArrayList);
        recyclerView.setAdapter(adapter);
    }


    private void CreateDataForCards(String text, int Month, int Day, int Year) {
        EventCards event = new EventCards(text, Month, Year, Day);
        eventArrayList.add(event);
        adapter.notifyDataSetChanged();
    }

    public void openPopup(View view) {
        PopupWindow popupWindow = new PopupWindow(requireContext());
        LayoutInflater inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        final EditText editText = popupView.findViewById(R.id.inputEditText);
        Button submitButton = popupView.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                Toast.makeText(requireContext(), "Submitted: " + text, Toast.LENGTH_SHORT).show();
                CreateDataForCards(text, mMonth, mDay, mYear);
                System.out.println("Month:" + mMonth);
                popupWindow.dismiss();
            }
        });

        popupWindow.setContentView(popupView);
        popupWindow.setWidth(600);
        popupWindow.setHeight(400);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}

package com.example.dungan_lakaw;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HotelActivity extends AppCompatActivity {

    private EditText searchBar;
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<String> allHotels;
    private List<String> filteredHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        searchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.hotelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        allHotels = generateHotelList();
        filteredHotels = new ArrayList<>(allHotels);

        hotelAdapter = new HotelAdapter(filteredHotels);
        recyclerView.setAdapter(hotelAdapter);

        setupSearchBar();
    }

    private void setupSearchBar() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterHotels(s.toString());
            }
        });
    }

    private void filterHotels(String query) {
        filteredHotels.clear();
        for (String hotel : allHotels) {
            if (hotel.toLowerCase().contains(query.toLowerCase())) {
                filteredHotels.add(hotel);
            }
        }
        hotelAdapter.notifyDataSetChanged();
    }

    private List<String> generateHotelList() {
        List<String> hotels = new ArrayList<>();
        hotels.add("Hotdog Hotel");
        hotels.add("CS284 Hotel");
        hotels.add("GLE202 Hotel");
//        hotels.add("Hotel D");
//        hotels.add("Hotel E");
//        hotels.add("Hotel F");
        return hotels;
    }

    private class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

        private List<String> hotels;

        public HotelAdapter(List<String> hotels) {
            this.hotels = hotels;
        }

        @NonNull
        @Override
        public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hotel, parent, false);
            return new HotelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
            String hotel = hotels.get(position);
            holder.hotelNameTextView.setText(hotel);

            // Set OnClickListener on the CardView
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click event
                    showBookingDialog(hotel);
                }
            });
        }

        @Override
        public int getItemCount() {
            return hotels.size();
        }

        class HotelViewHolder extends RecyclerView.ViewHolder {

            TextView hotelNameTextView;

            HotelViewHolder(@NonNull View itemView) {
                super(itemView);
                hotelNameTextView = itemView.findViewById(R.id.hotelNameTextView);
            }
        }
    }

    private void showBookingDialog(String hotelName) {
        DialogFragment bookingDialog = new BookingDialogFragment(hotelName);
        bookingDialog.show(getSupportFragmentManager(), "booking_dialog");
    }

    public static class BookingDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private static final String ARG_HOTEL_NAME = "hotel_name";

        private TextView checkInTextView;
        private TextView checkOutTextView;
        private String selectedHotelName;
        private Calendar checkInDate;
        private Calendar checkOutDate;
        private SimpleDateFormat dateFormat;

        public BookingDialogFragment() {
        }

        public BookingDialogFragment(String hotelName) {
            Bundle args = new Bundle();
            args.putString(ARG_HOTEL_NAME, hotelName);
            setArguments(args);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_booking, container, false);

            checkInTextView = view.findViewById(R.id.checkInTextView);
            checkOutTextView = view.findViewById(R.id.checkOutTextView);

            Bundle args = getArguments();
            if (args != null) {
                selectedHotelName = args.getString(ARG_HOTEL_NAME);
                getDialog().setTitle(selectedHotelName);
            }

            checkInDate = Calendar.getInstance();
            checkOutDate = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            checkInTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(true);
                }
            });

            checkOutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(false);
                }
            });

            return view;
        }

        private void showDatePicker(boolean isCheckIn) {
            Calendar currentDate = isCheckIn ? checkInDate : checkOutDate;
            int year = currentDate.get(Calendar.YEAR);
            int month = currentDate.get(Calendar.MONTH);
            int day = currentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set min date to today
            datePickerDialog.show();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);

            if (checkInTextView.getText() == "") {
                checkInDate = selectedDate;
                checkInTextView.setText(dateFormat.format(checkInDate.getTime()));
            } else {
                checkOutDate = selectedDate;
                checkOutTextView.setText(dateFormat.format(checkOutDate.getTime()));
            }
        }
    }
}

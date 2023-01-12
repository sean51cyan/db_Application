package com.example.dbapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dbapplication.R;
import com.example.dbapplication.TestOpenHelper;
import com.example.dbapplication.databinding.FragmentDashboardBinding;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private TestOpenHelper helper;
    public TextView textView;
    private Spinner spinner;
    private EditText showDate;
    private EditText taskData;
    private SQLiteDatabase db;
    private View root;
    private int y;
    private int m;
    private int d;
    private int w;
    private String task;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        String[] day_name = getResources().getStringArray(R.array.week_array);


        showDate = root.findViewById(R.id.showDate);
        showDate.setOnClickListener(v -> {
            //Calendarインスタンスを取得
            final Calendar date = Calendar.getInstance();

            //DatePickerDialogインスタンスを取得
            @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(
                    root.getContext(),
                    (view, year, month, dayOfMonth) -> {
                        //setした日付を取得して表示
                        showDate.setText(String.format("%d / %02d / %02d", year, month + 1, dayOfMonth));
                        y = year;
                        m = month + 1;
                        d = dayOfMonth;
                        final Calendar date2 = Calendar.getInstance();
                        date2.set(year, month, dayOfMonth);
                        w = date2.get(Calendar.DAY_OF_WEEK) - 1;
                    },
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DATE)
            );

            //dialogを表示
            datePickerDialog.show();

        });

        taskData = root.findViewById(R.id.taskData);

        spinner = root.findViewById(R.id.spinnerTitle);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (root.getContext(),
                        R.array.title_array,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        textView = root.findViewById(R.id.text_view);

        Button insertButton = root.findViewById(R.id.button_insert);
        insertButton.setOnClickListener(v -> {

            if (helper == null) {
                helper = new TestOpenHelper(root.getContext());
            }

            if (db == null) {
                db = helper.getWritableDatabase();
            }

            String title = (String) spinner.getSelectedItem();
            String date = day_name[w];
            @SuppressLint("DefaultLocale") String dl = String.format("%d-%02d-%02d", y, m, d);
            task = taskData.getText().toString();

            insertData(db, title, date, task, dl);
        });

        return root;
    }

    private void insertData(SQLiteDatabase db, String title, String date, String task, String dl) {

        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();
        values1.put("title", title);
        values1.put("date", date);
        values2.put("task", task);
        values2.put("dl", dl);
        values2.put("fin", false);

        db.insert("授業", null, values1);
        db.insert("課題", null, values2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
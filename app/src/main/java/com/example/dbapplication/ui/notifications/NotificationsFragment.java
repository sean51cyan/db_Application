package com.example.dbapplication.ui.notifications;

import static java.lang.Math.min;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dbapplication.R;
import com.example.dbapplication.TestOpenHelper;
import com.example.dbapplication.databinding.FragmentNotificationsBinding;



public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private TestOpenHelper helper;
    private TextView textView;
    private SQLiteDatabase db;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        textView = binding.textNotifications;

        Button readButton = root.findViewById(R.id.button_read);
        readButton.setOnClickListener(v -> readData());

        return root;
    }


    private void readData() {
        if (helper == null) {
            helper = new TestOpenHelper(root.getContext());
        }

        if (db == null) {
            db = helper.getReadableDatabase();
        }
        Log.d("debug", "**********Cursor");

        String sql = "SELECT * FROM 授業 INNER JOIN 課題 ON 授業._id = 課題._id";
        Cursor cursor = db.rawQuery(sql,null);

        cursor.moveToFirst();

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < min(cursor.getCount(),4); i++) {
            sbuilder.append("*********************\n");
            sbuilder.append(cursor.getString(1));
            sbuilder.append(": ");
            sbuilder.append(cursor.getString(2));
            sbuilder.append("曜日\n");
            sbuilder.append(cursor.getString(4));
            sbuilder.append("\n締切: ");
            sbuilder.append(cursor.getString(5));
            sbuilder.append("\n");

            cursor.moveToNext();
        }

        // 忘れずに！
        cursor.close();
        sbuilder.append("*********************");

        Log.d("debug", "**********" + sbuilder);
        textView.setText(sbuilder.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
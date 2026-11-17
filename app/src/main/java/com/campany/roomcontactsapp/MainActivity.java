package com.campany.roomcontactsapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etEmail;
    private AppDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRead = findViewById(R.id.btnRead);
        Button btnClear = findViewById(R.id.btnClear);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "contacts_db")
                .allowMainThreadQueries()
                .build();

        userDao = db.userDao();

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            if (!name.isEmpty() && !email.isEmpty()) {
                userDao.insert(new User(name, email));
                Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
            }
        });

        btnRead.setOnClickListener(v -> {
            List<User> users = userDao.getAllUsers();
            for (User u : users) {
                Log.d("SQL", "ID: " + u.getId() + " | Имя: " + u.getName() + " | Email: " + u.getEmail());
            }
            Toast.makeText(this, "Выведено в Logcat", Toast.LENGTH_SHORT).show();
        });

        btnClear.setOnClickListener(v -> {
            etName.setText("");
            etEmail.setText("");
        });
    }
}
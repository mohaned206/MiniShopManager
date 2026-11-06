package com.example.myapplicationminishopmanager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.etUser)
        val cbRemember = findViewById<CheckBox>(R.id.cbRemember)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        prefs = getSharedPreferences("ShopPrefs", MODE_PRIVATE)

        // Pré-remplir si "Se souvenir de moi" était coché
        etUser.setText(prefs.getString("USER", ""))
        cbRemember.isChecked = prefs.getBoolean("REMEMBER", false)

        btnLogin.setOnClickListener {
            val user = etUser.text.toString().trim()
            if (user.isEmpty()) {
                etUser.error = "Champ obligatoire"
                return@setOnClickListener
            }

            val editor = prefs.edit()
            if (cbRemember.isChecked) {
                editor.putString("USER", user)
                editor.putBoolean("REMEMBER", true)
            } else {
                editor.clear()
            }
            editor.apply()

            startActivity(Intent(this, CatalogueActivity::class.java))
            finish()
        }
    }
}
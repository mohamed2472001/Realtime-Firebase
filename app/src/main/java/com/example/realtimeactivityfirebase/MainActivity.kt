package com.example.realtimeactivityfirebase

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.realtimeactivityfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Firebase.database
        val myRef = database.getReference()

        binding.btnSave.setOnClickListener {
            val name = binding.personNameTxt.text.toString()
            val id = binding.personIdTxt.text.toString()
            val age = binding.personAgeTxt.text.toString()

            val person = hashMapOf(
                "name" to name, "id" to id, "age" to age
            )

            myRef.child("person").child("${count}").setValue(person)
            count++
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }

        binding.btnGetData.setOnClickListener {
            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue()
                    binding.textData.text = value.toString()
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()

                }

            })
        }


    }
}
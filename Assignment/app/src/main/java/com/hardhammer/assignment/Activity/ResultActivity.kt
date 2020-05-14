package com.hardhammer.assignment.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hardhammer.assignment.Adapter.CommonAdaptor
import com.hardhammer.assignment.R

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val drop=intent.getStringExtra("drop")
        val p=intent.getStringExtra("p")
        val list= intent.getParcelableArrayListExtra<fourm>("list")
        val recycelr=findViewById<RecyclerView>(R.id.rec2)
        val o1=findViewById<TextView>(R.id.ou1)
        val spinner=findViewById<Spinner>(R.id.spinner1)
        val spinnerData= arrayOf(drop)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerData)
        o1.text=p
        val card=findViewById<CardView>(R.id.card3)
        recycelr.layoutManager=LinearLayoutManager(this)
        recycelr.adapter= CommonAdaptor<fourm>(
            R.layout.single_row_result,
            list,
            { list, card -> bindItem(list, card) },
            true
        )

    }
    fun bindItem(list: fourm, view:View){
        val in3=view.findViewById<TextView>(R.id.ou3)
        val in4=view.findViewById<TextView>(R.id.ou4)
        val in5=view.findViewById<CheckBox>(R.id.ou5)
        in3.text=list.et1
        in4.text=list.et2
        if(list.et3.equals("yes")){
            in5.text="yes"
            in5.isChecked=true
        }
        else{
            in5.text="no"
            in5.isChecked=false
        }
    }
}

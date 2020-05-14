package com.hardhammer.assignment.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hardhammer.assignment.*
import com.hardhammer.assignment.Adapter.CommonAdaptor
import com.hardhammer.assignment.Adapter.SwipeToDeleteCallback
import kotlinx.android.parcel.Parcelize

@Parcelize
data class fourm(
    var id:Int=0,
    var et1:String="",
    var et2:String="",
    var et3:String=""
) : Parcelable

class FourmActivity : AppCompatActivity() {
lateinit var counter :ArrayList<fourm>
    var p=""
    var spinner_Selected="A"
    lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourm)
        counter= ArrayList()
        counter.add(fourm())

        val in1 = findViewById<TextView>(R.id.in1)
        in1.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               p=in1.text.toString()
            }

        })
        val add=findViewById<FloatingActionButton>(R.id.add)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinnerData = arrayOf("A", "B", "C", "D")

        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerData)
        spinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
              spinner_Selected= p0?.getItemAtPosition(p2).toString()
            }

        }
        val card=findViewById<CardView>(R.id.card2)
        recycler=findViewById<RecyclerView>(R.id.rec1)
        recycler.layoutManager=LinearLayoutManager(this)

        recycler.adapter=
            CommonAdaptor<fourm>(
                R.layout.single_row_expand,
                counter,
                { counter, card2 -> bindItem(counter, card2) },
                false
            )
        val s=object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id=viewHolder.itemView.findViewById<TextView>(R.id.id_card)
                val delList= fourm()
                delList.id=id.text.toString().toInt()
                counter.remove(delList)
                (recycler.adapter as CommonAdaptor<fourm>).removeAt(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(s).attachToRecyclerView(recycler)
        {
            if(counter.isEmpty()){
                counter.add(fourm())
                (recycler.adapter as CommonAdaptor<fourm>).add(counter)
            }
            else {
                val emp =
                    fourm(id = counter.first().id++)
                counter.add(emp)
                (recycler.adapter as CommonAdaptor<fourm>).add(counter)
            }
        }

    }
    private fun bindItem(list: fourm, view: View){
        val id=view.findViewById<TextView>(R.id.id_card)
        id.text=list.id.toString()
        println("${list.id}")
        val in3=view.findViewById<EditText>(R.id.in3)
        val in4=view.findViewById<EditText>(R.id.in4)
        val in5=view.findViewById<CheckBox>(R.id.in5)
        in3.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            { list.et1=in3.text.toString() }
        })
        in4.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
            { list.et2=in4.text.toString() }
        })
        in5.setOnClickListener {
            if(in5.isChecked) {
               list.et3="yes"
                in5.text="yes"
            }
            else{
                list.et3="no"
            in5.text="no"}
        }


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.Task1 ->{val intent= Intent(this,
                MainActivity::class.java)
                startActivity(intent)
            }
            R.id.save ->{
                val intent=Intent(this,
                    ResultActivity::class.java)

                intent.putExtra("list",counter)
                intent.putExtra("p",p)
                intent.putParcelableArrayListExtra("list",counter)
                intent.putExtra("drop",spinner_Selected)
                startActivity(intent)

            }

        }

        return super.onOptionsItemSelected(item)
    }

}
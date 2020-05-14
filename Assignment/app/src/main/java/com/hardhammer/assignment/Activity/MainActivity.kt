package com.hardhammer.assignment.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hardhammer.assignment.Adapter.CommonAdaptor
import com.hardhammer.assignment.Connection.connectionManager
import com.hardhammer.assignment.R
import com.hardhammer.assignment.apiResponse
import com.hardhammer.assignment.apiRetro
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pro=findViewById<ProgressBar>(R.id.pro)
        pro.visibility=View.VISIBLE
        if(connectionManager().checkConnection(this)){
        val retrofit=Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(apiRetro::class.java)

        val recycler=findViewById<RecyclerView>(R.id.recyler)
        recycler.layoutManager=LinearLayoutManager(this)
        val l= ArrayList<apiResponse>()
        val card=findViewById<RelativeLayout>(R.id.card)


        val list= retrofit.getGitResponse().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        list.subscribe{
            pro.visibility=View.GONE
          for(i in 0 until it.size){
              l.add(it[i])
              recycler.adapter= CommonAdaptor<apiResponse>(R.layout.single_row_recyler, l, { l, card -> bindItem(l, card) }, true)
          }
        }}
        else{
            val dialog= AlertDialog.Builder(this  as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Please connect to internet")
            dialog.setPositiveButton("ok") { text,listener->
                val settingsOpen= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsOpen)
                this.finish()

            }
            dialog.create()
            dialog.show()

        }


    }
    @SuppressLint("SetTextI18n")
    private fun bindItem(list: apiResponse, view:View){
        val id=view.findViewById<TextView>(R.id.id)
        val login=view.findViewById<TextView>(R.id.login)
        val state=view.findViewById<CheckBox>(R.id.state)
        val url=view.findViewById<TextView>(R.id.repo_url)
        state.isClickable=false
        id.text=list.user?.id.toString()
        login.text=list.user?.login
        url.text=list.repositoryurl
        when(list.state){
            "open"->{  state.isChecked=true
                state.text="open"}
            "close"->{
                state.isChecked=false
                state.text="close"
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task2,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.Task2 ->{
                val intent= Intent(this,
                    FourmActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}





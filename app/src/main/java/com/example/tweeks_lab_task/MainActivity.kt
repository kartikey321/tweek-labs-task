package com.example.tweeks_lab_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tweeks_lab_task.adapters.AthleteAdapter
import com.example.tweeks_lab_task.databinding.ActivityMainBinding
import com.example.tweeks_lab_task.models.Athlete
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        val bottomNavigationView = binding.bottomNavigatinView
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController
        )
//        val linerLayoutManager =LinearLayoutManager(applicationContext)
//        binding.leaderboardList.layoutManager=linerLayoutManager
//        try{
//            val obj = JSONObject(loadJSONFromAsset())
//            val list = obj.getJSONArray("athletes")
//            for ( i in 0 until list.length()){
//                var detail = list.getJSONObject(i)
//                val id = detail.getString("id")
//                val name =detail.getString("name")
//                val score = detail.getInt("score")
//                val runup = detail.getInt("runup")
//                val jump = detail.getInt("jump")
//                val bfc = detail.getInt("bfc")
//                val ffc = detail.getInt("ffc")
//                val release = detail.getInt("release")
//                var athlete = Athlete(id,name,score, runup,jump, bfc, ffc, release )
//                athletes.add(athlete)
//
//            }
//
//        } catch (e: JSONException) {
//            print("error found: $e")
//            e.printStackTrace()
//        }
//
//
//        val adapter = AthleteAdapter(this,athletes)
//        adapter.run{
//         notifyDataSetChanged()
//        }
//        binding.leaderboardList.adapter=adapter


    }

    private fun loadJSONFromAsset(): String {
        val json: String?

            val inputStream = assets.open("leaderboard_d.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
            print("json found: $json")


        return json
    }

    fun load_json(): String? {
        var json:String?=null

        try{
            val inputStream: InputStream = assets.open("leaderboard_d.json")
            json = inputStream.bufferedReader().use { it.readText() }

        }catch (e: IOException){
            print("error found: "+e);
        }

        return json

    }
}
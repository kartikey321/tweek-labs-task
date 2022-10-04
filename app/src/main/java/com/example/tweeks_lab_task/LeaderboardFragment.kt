package com.example.tweeks_lab_task

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tweeks_lab_task.adapters.AthleteAdapter
import com.example.tweeks_lab_task.adapters.SearchAdapter
import com.example.tweeks_lab_task.databinding.BottomsheetlayoutBinding
import com.example.tweeks_lab_task.databinding.FragmentLeaderboardBinding
import com.example.tweeks_lab_task.models.Athlete
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    val filteredArray: ArrayList<Athlete> = ArrayList()
    var sortParam:String="Sort"
     var _binding: FragmentLeaderboardBinding?=null
    var binding1 : BottomsheetlayoutBinding?=null;
    var athletes:ArrayList<Athlete> = ArrayList()
    var adapter:AthleteAdapter?=null
    var searchAdapter:SearchAdapter?=null
    var index:Int=0
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)


        val searchlinerLayoutManager = LinearLayoutManager(context)
        binding.searchRecycler.layoutManager=searchlinerLayoutManager

                val linerLayoutManager = LinearLayoutManager(context)
        binding.leaderboardList.layoutManager=linerLayoutManager
        try{
            val obj = JSONObject(loadJSONFromAsset())
            val list = obj.getJSONArray("athletes")
            for ( i in 0 until list.length()){
                var detail = list.getJSONObject(i)
                val id = detail.getString("id")
                val name =detail.getString("name")
                val score = detail.getInt("score")
                val runup = detail.getInt("runup")
                val jump = detail.getInt("jump")
                val bfc = detail.getInt("bfc")
                val ffc = detail.getInt("ffc")
                val release = detail.getInt("release")
                var athlete = Athlete(id,name,score, runup,jump, bfc, ffc, false,release )
                athletes.add(athlete)

            }

        } catch (e: JSONException) {
            print("error found: $e")
            e.printStackTrace()
        }


         adapter = AthleteAdapter(requireContext(),athletes)

        binding.leaderboardList.adapter=adapter

        binding.bottomCard.setOnClickListener {
            showDialog()
        }
        binding.leaderboardList.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var firstPos=linerLayoutManager.findFirstVisibleItemPosition()
                var lastPos = linerLayoutManager.findLastVisibleItemPosition()
                for ( i in athletes){
                    if(i.name == "Joseph Hermis"){
                        index=athletes.indexOf(i)
                    }
                }
                if(index>= firstPos && index<=lastPos){
                    binding.myScore.visibility=View.GONE
                }
                else{
                    binding.myScore.visibility=View.VISIBLE

                }
                for (i in linerLayoutManager.findFirstCompletelyVisibleItemPosition() .. linerLayoutManager.findLastCompletelyVisibleItemPosition()){

                }
            }
        })

        binding.myScore.setOnClickListener{
            linerLayoutManager.scrollToPosition(index)
            binding.myScore.visibility=View.GONE
        }

        binding.searchEdit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                searchAdapter = SearchAdapter(requireContext(),filteredArray)

                binding.searchRecycler.adapter=searchAdapter

               filterArray(s.toString())

            }
        })

        binding.search.setOnClickListener{
            binding.overlayScreen.visibility=View.VISIBLE
            binding.searchOverlay.visibility=View.VISIBLE

        }

        binding.cancel.setOnClickListener {
            try {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
            } catch (e: Exception) {
                // TODO: handle exception
            }
            binding.overlayScreen.visibility=View.GONE
            binding.cancel.visibility=View.GONE
            binding.searchOverlay.visibility=View.GONE
        }

        binding.searchEdit.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
            if (b){
                // do something when edit text get focus
                binding.cancel.visibility=View.VISIBLE
              binding.searchOverlay.visibility=View.VISIBLE

            }else{

                // do something when edit text lost focus

                // hide soft keyboard when edit text lost focus

            }
        }

        val view = binding.root
        return view


    }

    private fun filterArray(str: String) {
filteredArray.clear()

        for(item in athletes){
            if(item.name.lowercase().contains(str.lowercase())){
                filteredArray.add(item)
                searchAdapter!!.run {
                    notifyDataSetChanged()
                }
            }
        }


    }

    private fun showDialog() {
       var dialog = BottomSheetDialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheetlayout)
        val cancel = dialog.findViewById<ImageView>(R.id.cancel)
        cancel!!.setOnClickListener{

            dialog.dismiss()
            binding.fragLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
            binding.overlayScreen.visibility=View.GONE
        }

        val layout = dialog.findViewById<LinearLayout>(R.id.layout1)
     layout!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.overlayScreen.visibility=View.VISIBLE

        val group = dialog.findViewById<RadioGroup>(R.id.radioGroup)
        group!!.clearCheck()

        group!!.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton? = dialog.findViewById(checkedId)
                Toast.makeText(requireContext()," On checked change :"+
                        " ${radio!!.text}",
                    Toast.LENGTH_SHORT).show()
                sortParam=radio!!.text.toString()
                 when(radio!!.text){
                    "Score" -> {

                        athletes.sortBy {
                            it.id
                        }
                    }
                    "Run-Up" -> {
                        athletes.sortBy {
                            it.runup
                        }
                    }
                    "Jump" -> {
                        athletes.sortBy {
                            it.jump
                        }
                    }
                    "Back-Foot Contact" -> {
                        athletes.sortBy {
                            it.bfc
                        }
                    }

                     "Front-Foot Contact" -> {
                         athletes.sortBy {
                             it.ffc
                         }
                     }

                     "Release" -> {
                         athletes.sortBy {
                             it.release
                         }
                     }



                    "ffc" -> AthleteParameters.Front_Foot_Control
                    "release" -> AthleteParameters.Release
                    else -> {
                        AthleteParameters.Score
                    }

                }
                binding.sortParam.text=radio!!.text.toString()
                adapter!!.run {
                    notifyDataSetChanged()
                }
            })
        dialog.show()
        dialog.setCancelable(false)



        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
       dialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations=R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

    }

    fun loadJSONFromAsset(): String {
        val json: String?

        val inputStream = requireContext().getAssets()?.open("leaderboard_d.json")
        val size = inputStream?.available()
        val buffer = ByteArray(size!!)
        val charset: Charset = Charsets.UTF_8
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, charset)
        print("json found: $json")


        return json
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
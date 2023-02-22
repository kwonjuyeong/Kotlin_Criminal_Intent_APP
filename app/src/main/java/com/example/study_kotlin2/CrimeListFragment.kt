package com.example.study_kotlin2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID
import javax.security.auth.callback.Callback

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    /**
     * 호스팅 액티비티에서 구현할 인터페이스
     */
    interface Callbacks{
        fun onCrimeSelected(crimeId : UUID)
    }

    private var callbacks: Callbacks ?= null

    private lateinit var crimeRecyclerView : RecyclerView
    private var adapter : CrimeAdapter ?= CrimeAdapter(emptyList())

    private val crimeListViewModel : CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(crimes : List<Crime>){
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view:View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime : Crime
        val titleText : TextView = itemView.findViewById(R.id.crime_title)
        val dateText : TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView : ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime){
            this.crime = crime
            titleText.text = this.crime.title
            dateText.text = this.crime.date.toString()
            solvedImageView.visibility = if(crime.isSolved){
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    //Adapter는 RecyclerView와 List<Crime> 사이에 위치한다.
    private inner class CrimeAdapter(var crimes : List<Crime>): RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun getItemCount() = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]

            holder.bind(crime)
        }
    }


    companion object{
        fun newInstance():CrimeListFragment{
            return CrimeListFragment()
        }
    }
}
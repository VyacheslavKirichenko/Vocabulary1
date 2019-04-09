package com.example.vocabulary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.DropBoxManager
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private var wordToDefn = HashMap<String, String>()
    private val words = java.util.ArrayList<String>()
    private val defns = java.util.ArrayList<String>()
    private lateinit var myAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readDictionaryFile()
        setupList()
        definitions_list.setOnItemClickListener { _, _, index, _ ->

            if (defns[index] == wordToDefn[textView.text]) {
                val toast = Toast.makeText(applicationContext, "Correct", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val toast = Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_SHORT)
                toast.show()
            }
            defns.removeAt(index)
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun readDictionaryFile() {
        val reader = Scanner(resources.openRawResource(R.raw.grewords))
        while (reader.hasNextLine()) {
            //come	came	come
            val line = reader.nextLine()
            Log.d("Marty", "the line is ----> $line")
            val pieces = line.split("\t")
            if (pieces.size >= 2) {
                words.add(pieces[0])
                wordToDefn.put(pieces[0], pieces[1])
            }
        }
    }

    private fun setupList() {

        val rand = Random()
        val index = rand.nextInt(words.size)
        val word = words[index]
        textView.text = word
        defns.clear()
        defns.add(wordToDefn[word]!!)
        words.shuffle()
        for (otherWords in words.subList(0, 9)) {
            if (otherWords == word || defns.size == 10) {
                continue
            }
            defns.add(wordToDefn[otherWords]!!)
        }
        defns.shuffle()
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, defns)

        definitions_list.adapter = myAdapter
    }
}

package com.wisnu.notetransaksi

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class RekActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rek)
        var Dbase: SQLiteDatabase = openOrCreateDatabase(Consts.DB_NAME, Context.MODE_PRIVATE, null)
        Dbase.execSQL("create table if not exists rekening(_id integer primary key autoincrement,empId varchar(50), empName varchar(50),empAtas varchar(50))")

        btn_insert.setOnClickListener {
        var cv: ContentValues = ContentValues()
            cv.put("empId", et_empID.text.toString())
            cv.put("empName", et_empname.text.toString().toInt())
            cv.put("empAtas", et_empPhone.text.toString())

            var status:Long = Dbase.insert(Consts.TABLE_EMPLOYEE2, null, cv)


            if(status!=-1L)
            {
                Toast.makeText(this@RekActivity, "Insertion Successful", Toast.LENGTH_SHORT).show()
                et_empID.setText("")
                et_empPhone.setText("")
                et_empname.setText("")
            }
            else{
                Toast.makeText(this@RekActivity, "Insertion failed", Toast.LENGTH_SHORT).show()
            }
        }


        btn_update.setOnClickListener {
            var cv: ContentValues = ContentValues()
            cv.put("empName", et_empname.text.toString())
            cv.put("empId", et_empPhone.text.toString())
            var status =   Dbase.update(
                Consts.TABLE_EMPLOYEE2,
                cv,
                "empAtas=?",
                arrayOf(et_empID.text.toString())
            )

            if(status!=0)
            {
                Toast.makeText(this@RekActivity, "Updation Successful", Toast.LENGTH_SHORT).show()
                et_empID.setText("")
                et_empPhone.setText("")
                et_empname.setText("")
            }
            else{
                Toast.makeText(this@RekActivity, "Updation Failed", Toast.LENGTH_SHORT).show()
            }
        }

        btn_delete.setOnClickListener {

            var status =     Dbase.delete(
                Consts.TABLE_EMPLOYEE2,
                "empAtas=?",
                arrayOf(et_empID.text.toString())
            )

            if(status!=0)
            {
                Toast.makeText(this@RekActivity, "Deletion Successful", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@RekActivity, "Deletion Failed", Toast.LENGTH_SHORT).show()
            }
        }

        btn_read.setOnClickListener {

            var c: Cursor = Dbase.query(Consts.TABLE_EMPLOYEE2, null, null, null, null, null, null)

            var from = arrayOf("empId", "empName", "empAtas")
            var to = intArrayOf(R.id.tv_id, R.id.tv_name, R.id.tv_phone)
            var myadapter = SimpleCursorAdapter(
                this@RekActivity,
                R.layout.list_item,
                c,
                from,
                to,
                0
            )

            lv.adapter = myadapter
        }
    }

}
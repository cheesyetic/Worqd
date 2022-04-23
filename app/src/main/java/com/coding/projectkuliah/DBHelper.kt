package com.coding.projectkuliah

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.coding.projectkuliah.model.ProductModel
import com.coding.projectkuliah.model.UserModel
import java.sql.Blob


class DBHelper(context: Context): SQLiteOpenHelper(context, "Login.db", null, 1) {

    companion object{
        private val KEY_ACCOUNT_ID = "user_id"
    }

    override fun onCreate(MyDB: SQLiteDatabase) {
        //Creating Table
        MyDB.execSQL("CREATE TABLE users(user_id INTEGER primary key, name TEXT, username TEXT, email TEXT, password TEXT, phone TEXT, logged_in INTEGER, profile blob)")
        MyDB.execSQL("CREATE TABLE category(category_id INTEGER primary key, category_name TEXT, category_detail TEXT)")
        MyDB.execSQL("CREATE TABLE product(product_id INTEGER primary key, product_name TEXT, product_category TEXT, product_price TEXT, product_detail TEXT, product_phone TEXT, product_email TEXT, longitude TEXT, latitude TEXT)")

        //Inserting Data in Category
        val insertcategory = "insert into category(category_id, category_name, category_detail)"
        MyDB.execSQL(insertcategory + "values(1, 'Graphics and Design', 'Logo and Brand Identity, Art and Illustration')")
        MyDB.execSQL(insertcategory + "values(2, 'Digital Marketing', 'Social Media Marketing, Social Media Advertising')")
        MyDB.execSQL(insertcategory + "values(3, 'Writing & Translation', 'Article and Blog Posts, Translation')")
        MyDB.execSQL(insertcategory + "values(4, 'Video & Animation', 'Whiteboard and Animation Explainers, Video Editing')")
        MyDB.execSQL(insertcategory + "values(5, 'Programming & Tech', 'Wordpress, Website Builder and CMS')")
        MyDB.execSQL(insertcategory + "values(6, 'Data', 'Database, Data Processing')")
        MyDB.execSQL(insertcategory + "values(7, 'Business', 'Virtual Assistant, Market Research')")
        MyDB.execSQL(insertcategory + "values(8, 'Lifestyle', 'Online Tutoring, Gaming')")

        //Inserting Data in Product
        val insertproduct = "insert into product(product_id, product_name, product_category, product_price, product_detail, product_phone, product_email, longitude, latitude)"
        MyDB.execSQL(insertproduct + "values(1, 'Izdeveloper', '1', 'IDR200.000', 'We care about your design project!', '08111111', 'a@m.c', '-170.68004', '8.51495')")
        MyDB.execSQL(insertproduct + "values(2, 'Whoami', '1', 'IDR700.000', 'You can trust us!', '08111112', 'b@m.c', '139.90164', '-31.21078')")
        MyDB.execSQL(insertproduct + "values(3, 'Wala Ng Ako', '1', 'IDR500.000', 'Wala wala we are the best!', '08111113', 'c@m.c', '-119.62409', '-44.18784')")
        MyDB.execSQL(insertproduct + "values(4, 'Whoeveritis', '2', 'IDR400.000', 'Why dont u choose us?', '08111142', 'd@m.c', '-12.18338', '11.48178')")
        MyDB.execSQL(insertproduct + "values(5, 'ILoveU', '2', 'IDR300.000', 'Yep, I love u!', '08111118', 'e@m.c', '157.71507', '47.88205')")
        MyDB.execSQL(insertproduct + "values(6, 'IStillLoveU', '3', 'IDR100.000', 'Yep, I still love u!', '08111122', 'f@m.c', '-126.18414', '69.45760')")
        MyDB.execSQL(insertproduct + "values(7, 'Hadouken', '3', 'IDR200.000', 'Hadouken Hadouken Hadouken!', '08111812', 'g@m.c', '91.48642', '54.05762')")
        MyDB.execSQL(insertproduct + "values(8, 'Rimuru', '4', 'IDR100.000', 'I am reincarnated as a slime!', '08111102', 'h@m.c', '-55.94949', '-10.66846')")
        MyDB.execSQL(insertproduct + "values(9, 'Ideaingudahstuck', '5', 'IDR300.000', 'Ngasi deskripsi apalagi ya', '08133112', 'i@m.c', '-1.10708', '22.58339')")
        MyDB.execSQL(insertproduct + "values(10, 'Noname', '6', 'IDR900.000', 'Its just about data bro', '08111172', 'j@m.c', '-121.77530', '56.63269')")
        MyDB.execSQL(insertproduct + "values(11, 'Unnamed', '7', 'IDR690.000', 'Business is everything!', '08111812', 'k@m.c', '114.15069', '48.77322')")
        MyDB.execSQL(insertproduct + "values(12, 'Untitled', '8', 'IDR730.000', 'Your lifestyle is the most important thing', '08111612', 'l@m.c', '22.79785', '-24.36511')")

    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("drop Table if exists users")
        MyDB.execSQL("drop Table if exists category")
        MyDB.execSQL("drop Table if exists product")
        onCreate(MyDB)
    }

    fun insertData(username: String?, email: String?, password: String?, image: ByteArray): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", "")
        contentValues.put("username", username)
        contentValues.put("email", email)
        contentValues.put("password", password)
        contentValues.put("phone", "")
        contentValues.put("logged_in", "-1")
        contentValues.put("profile", image)

        val result = MyDB.insert("users", null, contentValues)
        return if (result == -1L) {
            false
        } else {
            true
        }
    }

    fun checkUsername(username: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from users where username = ?", arrayOf(username))
        return if (cursor.count > 0) true else false
    }

    fun chekUsernamePassword(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery(
            "Select * from users where username = ? and password = ?",
            arrayOf(username, password)
        )
        var res = true
        if (cursor.count > 0) {
            val updateQuery = "UPDATE users SET logged_in = 1 WHERE username = '$username' ;"
            val cursor2 = MyDB.rawQuery(updateQuery, null)
            try {
                if (cursor2.moveToFirst()) {
                }
            } finally {
                cursor2.close()
            }
        } else {
            res = false
        }
        cursor.close()
        MyDB.close()
        return res
    }

    fun checkAccount(): Int {
        val db = this.writableDatabase

        val checkQuery = "SELECT * FROM users WHERE logged_in = 1 LIMIT 1;"
        val cursor: Cursor = db.rawQuery(checkQuery, null)
        var loggedAccount = -1
        if (cursor.count > 0) {
            if (cursor.moveToFirst()) {
                do {
                    loggedAccount = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ACCOUNT_ID))
//                }
                } while (cursor.moveToNext())
            }
        }
        Log.d("CREATION", "Account logged id:$loggedAccount")
        cursor.close()
        db.close()
        return loggedAccount
    }

    fun logoutAccount(id: Int): Boolean {
        val db = this.writableDatabase

        val getQuery = "SELECT * FROM users WHERE $KEY_ACCOUNT_ID = $id;"
        val cursor: Cursor = db.rawQuery(getQuery, null)
        var res = true
        if (cursor.count > 0) {
            val updateQuery = "UPDATE users SET logged_in = -1 WHERE $KEY_ACCOUNT_ID = $id ;"
            val cursor2 = db.rawQuery( updateQuery,null )
            try {
                if (cursor2.moveToFirst()) {
                }
            } finally {
                cursor2.close()
            }
        } else {
            res = false
        }

        Log.d("CREATION", "Logout account, id:$id")
        try {
            if (cursor.moveToFirst()) {
            }
        } finally {
            cursor.close()
        }
        db.close()
        return res
    }

    fun viewProduct(id: String): List<ProductModel> {
        val empList: ArrayList<ProductModel> = ArrayList<ProductModel>()
        val selectQuery = "SELECT * FROM product WHERE product_category = $id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var product_id: Int
        var product_name: String
        var product_price: String
        var product_detail: String
        var longitude: String
        var latitude: String
        var product_phone: String
        var product_email: String
        if (cursor.moveToFirst()) {
            do {
                product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"))
                product_name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"))
                product_price = cursor.getString(cursor.getColumnIndexOrThrow("product_price"))
                product_detail = cursor.getString(cursor.getColumnIndexOrThrow("product_detail"))
                product_email = cursor.getString(cursor.getColumnIndexOrThrow("product_email"))
                product_phone = cursor.getString(cursor.getColumnIndexOrThrow("product_phone"))
                longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"))
                latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"))
                val emp = ProductModel(id = product_id, name = product_name, price = product_price,
                    detail = product_detail, email = product_email, phone = product_phone, longitude = longitude, latitude = latitude)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return empList
    }

    fun viewFourProduct(): List<ProductModel> {
        val empList: ArrayList<ProductModel> = ArrayList<ProductModel>()
        val selectQuery = "SELECT * FROM product LIMIT 4"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var product_id: Int
        var product_name: String
        var product_price: String
        var product_detail: String
        var longitude: String
        var latitude: String
        var product_phone: String
        var product_email: String
        if (cursor.moveToFirst()) {
            do {
                product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"))
                product_name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"))
                product_price = cursor.getString(cursor.getColumnIndexOrThrow("product_price"))
                product_detail = cursor.getString(cursor.getColumnIndexOrThrow("product_detail"))
                product_email = cursor.getString(cursor.getColumnIndexOrThrow("product_email"))
                product_phone = cursor.getString(cursor.getColumnIndexOrThrow("product_phone"))
                longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"))
                latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"))
                val emp = ProductModel(id = product_id, name = product_name, price = product_price,
                    detail = product_detail, email = product_email, phone = product_phone, longitude = longitude, latitude = latitude)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return empList
    }

    fun viewProductDetail(id: Int): List<ProductModel> {
        val empList: ArrayList<ProductModel> = ArrayList<ProductModel>()
        val selectQuery = "SELECT * FROM product WHERE product_id = $id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var product_id: Int
        var product_name: String
        var product_price: String
        var product_detail: String
        var longitude: String
        var latitude: String
        var product_phone: String
        var product_email: String
        if (cursor.moveToFirst()) {
            do {
                product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"))
                product_name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"))
                product_price = cursor.getString(cursor.getColumnIndexOrThrow("product_price"))
                product_detail = cursor.getString(cursor.getColumnIndexOrThrow("product_detail"))
                longitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"))
                latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"))
                product_email = cursor.getString(cursor.getColumnIndexOrThrow("product_email"))
                product_phone = cursor.getString(cursor.getColumnIndexOrThrow("product_phone"))
                val emp = ProductModel(id = product_id, name = product_name, price = product_price,
                    detail = product_detail, email = product_email, phone = product_phone, longitude = longitude, latitude = latitude)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return empList
    }

    fun viewUserDetail(id: Int): List<UserModel> {
        val empList: ArrayList<UserModel> = ArrayList<UserModel>()
        val selectQuery = "SELECT * FROM users WHERE user_id = $id"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var user_id: Int
        var name: String
        var username: String
        var password: String
        var phone: String
        var email: String
        var logged_in: Int
        var profile: ByteArray
        if (cursor.moveToFirst()) {
            do {
                user_id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                logged_in = cursor.getInt(cursor.getColumnIndexOrThrow("logged_in"))
                profile = cursor.getBlob(cursor.getColumnIndexOrThrow("profile"))
                val emp = UserModel(user_id = user_id, user_name = name, username = username,
                    password = password, email = email, logged_in = logged_in, phone = phone, image = profile)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return empList
    }

    fun updateUserDetail(id: Int, name: String, username: String, email: String, phone:String){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("username", username)
        contentValues.put("email", email)
        contentValues.put("phone", phone)

        db.update("users", contentValues, "user_id=?", arrayOf(id.toString()))

        db.close()
    }

    fun UpdateProfilePhoto(id: Int?, photo: ByteArray){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("profile", photo)
        db.update("users", contentValues, "user_id=?", arrayOf(id.toString()))
        db.close()
    }
}
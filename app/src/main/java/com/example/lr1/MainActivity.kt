package com.example.lr1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import androidx.appcompat.widget.ButtonBarLayout

private var currentIndex = 0
class MainActivity : AppCompatActivity() {

    data class Book(
        val imageName: String,
        val title: String,
        val author: String,
        val year: Int
    )
    fun loadBooksFromAssets(): List<Book> {
        val books = mutableListOf<Book>()
        val assetManager = assets
        try {
            assetManager.open("books.txt").bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val parts = line.split("\t")
                    if (parts.size >= 4) {
                        val imageName = parts[0]
                        val title = parts[1]
                        val author = parts[2]
                        val year = parts[3].toIntOrNull() ?: 0
                        books.add(Book(imageName, title, author, year))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return books
    }
    fun displayBook(book: Book) {
        val imageResId = resources.getIdentifier(book.imageName, "drawable", packageName)
        findViewById<ImageView>(R.id.bookImageView).setImageResource(imageResId)
        findViewById<TextView>(R.id.bookTitle).text = book.title
        findViewById<TextView>(R.id.bookDescription).text = "${book.author}, ${book.year}"
    }

    private lateinit var books : List<Book>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        books = loadBooksFromAssets()

        displayBook(books[currentIndex]);
        findViewById<Button>(R.id.nextBookButton).setOnClickListener {
            currentIndex = (currentIndex + 1) % books.size

            displayBook(books[currentIndex]);
        }
    }
}
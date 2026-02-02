import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BundleCompat.getParcelableArrayList
import java.io.Serializable

// Define a data class to hold book information neatly
data class Book(
    val title: String,
    val author: String,
    val rating: Int,
    val comment: String?
) : Serializable // Implement Serializable to pass the list via Intent

class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var viewButton: Button
    private lateinit var exitButton: Button

    // Use an ArrayList of the Book data class
    private val bookList = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.addButton)
        viewButton = findViewById(R.id.viewButton)
        exitButton = findViewById(R.id.exitButton)

        addButton.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            val inflater = layoutInflater
            // Ensure you have a dialog_add_book.xml layout file
            val dialogView = inflater.inflate(R.layout.dialog_add_book, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            dialog.show()

            // Ensure these IDs match your dialog_add_book.xml layout file exactly
            val titleEditText = dialogView.findViewById<EditText>(R.id.title_edit_text)
            val authorEditText = dialogView.findViewById<EditText>(R.id.author_edit_text)
            val ratingEditText = dialogView.findViewById<EditText>(R.id.rating_edit_text)
            val commentEditText = dialogView.findViewById<EditText>(R.id.comment_edit_text)
            val addBookButton = dialogView.findViewById<Button>(R.id.add_book_button)

            addBookButton.setOnClickListener {
                val titleStr = titleEditText.text.toString()
                val authorStr = authorEditText.text.toString()
                val ratingStr = ratingEditText.text.toString()
                val commentStr = commentEditText.text.toString()

                if (titleStr.isNotEmpty() && authorStr.isNotEmpty() && ratingStr.isNotEmpty()) {
                    try {
                        val ratingInt = ratingStr.toInt()
                        val newBook = Book(titleStr, authorStr, ratingInt, commentStr)
                        bookList.add(newBook)

                        Log.d("MainActivity", "Book added: ${newBook.title}")
                        Toast.makeText(this, "Book added: ${newBook.title}", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } catch (e: NumberFormatException) {
                        Toast.makeText(this, "Invalid rating format. Please enter a number.", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewButton.setOnClickListener {
            val intent = Intent(this, BookReviewActivity::class.java)
            // Pass the entire list using the standard key "bookList"
            intent.putExtra("bookList", bookList as Serializable)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finish()
        }
    }
}

interface AppCompatActivity {

}

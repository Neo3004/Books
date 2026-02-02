import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

interface Bundle {

}



interface TextView {

}



interface Button {

}



class BookReviewActivity : AppCompatActivity() {

    private lateinit var displayButton: Button
    private lateinit var averageButton: Button
    private lateinit var backButton: Button
    private lateinit var bookListTextView: TextView

    private var title = arrayOfNulls<String>(100)
    private var author = arrayOfNulls<String>(100)
    private var rating = IntArray(100)
    private var comment = arrayOfNulls<String>(100)
    private var bookCount = 0

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_review)

        displayButton = findViewById(R.id.displayButton)
        averageButton = findViewById(R.id.averageButton)
        backButton = findViewById(R.id.backButton)
        bookListTextView = findViewById(R.id.bookListTextView)

        title = intent.getStringArrayExtra("title")
        author = intent.getStringArrayExtra("author")
        rating = intent.getIntArrayExtra("rating")!!
        comment = intent.getStringArrayExtra("comment")
        bookCount = intent.getIntExtra("bookCount", 0)

        displayButton.setOnClickListener {
            val bookList = StringBuilder()
            for (i in 0 until bookCount) {
                bookList.append("${title[i]} by ${author[i]} - Rating: ${rating[i]} - Comment: ${comment[i]}\n")
            }
            bookListTextView.text = bookList.toString()
            Log.d("BookReviewActivity", "Book list displayed")
        }

        averageButton.setOnClickListener {
            var sum = 0
            for (i in 0 until bookCount) {
                sum += rating[i]
            }
            val average = if (bookCount > 0) sum.toDouble() / bookCount else 0.0
            bookListTextView.text = "Average rating: $average"
            Log.d("BookReviewActivity", "Average rating calculated")
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}

private fun BookReviewActivity.finish() {}

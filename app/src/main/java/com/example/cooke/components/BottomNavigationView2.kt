import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cooke.R
import com.example.cooke.activities.CommunityActivity
import com.example.cooke.activities.MainActivity
import com.example.cooke.activities.PostActivity
import com.example.cooke.activities.ProfileActivity
import com.example.cooke.activities.RecipeActivity

class BottomNavigationView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        // Inflate the XML layout for the component
        LayoutInflater.from(context).inflate(R.layout.view_bottom_navigation2, this, true)

        // Set up click listeners for the ImageButtons

        // Example: Set click listener for the home button
        val actionHome = findViewById<ImageButton>(R.id.action_home)
        actionHome.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        // Example: Set click listener for the community button
        val actionCommunity = findViewById<ImageButton>(R.id.action_community)
        actionCommunity.setOnClickListener {
            val intent = Intent(context, CommunityActivity::class.java)
            context.startActivity(intent)
        }

        val actionScan = findViewById<ImageButton>(R.id.action_scan)
        actionScan.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            context.startActivity(intent)
        }

        val actionRecipe = findViewById<ImageButton>(R.id.action_recipe)
        actionRecipe.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            context.startActivity(intent)
        }

        val actionNutritionist = findViewById<ImageButton>(R.id.action_nutritionist)
        actionNutritionist.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }

        layoutParams?.let {
            this@BottomNavigationView2.layoutParams = it
        }

        // Set click listeners for other ImageButtons similarly
        // ...

    }

}

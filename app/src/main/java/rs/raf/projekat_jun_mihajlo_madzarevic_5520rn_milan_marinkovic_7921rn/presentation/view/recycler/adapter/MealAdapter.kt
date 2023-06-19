package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.R

class MealAdapter(diffCallback: DiffUtil.ItemCallback<MealEntity>) : ListAdapter<MealEntity, MealAdapter.MealViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener{
            //OTVORI DETALJAN PROZOR ZA MEAL
        }
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(meal: MealEntity){
            itemView.findViewById<TextView>(R.id.mealTitle).text = meal.strMeal

            Picasso.get().load(meal.strMealThumb).into(itemView.findViewById(R.id.mealImage) as ImageView)
        }
    }
}
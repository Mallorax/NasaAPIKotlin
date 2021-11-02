package com.example.spaceinformer.ui

import android.view.View
import android.widget.ImageView
import com.example.spaceinformer.R
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.google.android.material.snackbar.Snackbar

// returns true if item is not null and navigation is viable
fun onItemImageClick(item: DomainIvItem?, view: View): Boolean{
    val nasaID = item?.nasaId
     if (nasaID != null) {
        Snackbar.make(
            view.context,
            view,
            "Navigation to details of: " + item.title,
            Snackbar.LENGTH_LONG
        ).show()
        return true
    } else {
        Snackbar.make(
            view.context,
            view,
            "Id of an item not found: " + item?.title,
            Snackbar.LENGTH_LONG
        ).show()
        return false
    }
}

//returns true if item was changed to favourite
fun onItemFavouriteClick(item: DomainIvItem?, view: ImageView): DomainIvItem{
    if (item?.favourite == false) {
        view.setImageResource(R.drawable.ic_baseline_favorite_24)
        Snackbar.make(
            view.context,
            view,
            "Favorite: " + item.title,
            Snackbar.LENGTH_LONG
        ).show()
        item.favourite = true
        return item
    } else {
        view.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        Snackbar.make(
            view.context,
            view,
            "Not favorite: " + item?.title,
            Snackbar.LENGTH_LONG
        ).show()
        item?.favourite = false
        return item!!
    }
}
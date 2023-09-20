package com.example.shoppinglist

class firebasemodel2 {

    private var item: String? = null
    var isChecked: Boolean? = null
    var isFavorite: Boolean? = null



   constructor() {
        // Default constructor
    }

    constructor(item: String, isChecked:Boolean, isFavorite:Boolean) {
        this.item = item
        this.isChecked = isChecked
        this.isFavorite = isFavorite
    }

    fun getItem(): String? {
        return item
    }

    fun setItem(item: String) {
        this.item = item
    }

    fun getIsChecked(): Boolean? {
        return isChecked
    }

    fun setIsChecked(isChecked: Boolean) {
        this.isChecked = isChecked
    }

    fun getIsFavorite(): Boolean? {
        return isFavorite
    }

    fun setIsFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
    }
}
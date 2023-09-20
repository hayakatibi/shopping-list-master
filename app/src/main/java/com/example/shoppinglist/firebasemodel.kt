package com.example.shoppinglist

class firebasemodel {
    private var title: String? = null

    constructor() {
        // Default constructor
    }

    constructor(title: String) {
        this.title = title
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

}







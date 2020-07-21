package com.example.myfavapi.model

class FavItem {
    var item_title: String? = null
    var key_id: String? = null
    var item_image: String? = null

    constructor() {}
    constructor(item_title: String?, key_id: String?, item_image: String?) {
        this.item_title = item_title
        this.key_id = key_id
        this.item_image = item_image
    }

}


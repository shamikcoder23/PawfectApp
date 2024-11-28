package com.example.myapplication

data class Pet (private var img : Int, private var name : String, private var desc : String) {
//    companion object {
//        fun createDogsList (img : ImageView, dogName : String) : ArrayList<Dog> {
//            val lst = ArrayList<Dog>()
//            lst.add(Dog(img, dogName))
//            return lst
//        }
//    }
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImg(): Int {
        return img
    }

    fun setImg(img: Int) {
        this.img = img
    }

    fun getDesc(): String {
        return desc
    }

    fun setDesc(desc: String) {
        this.desc = desc
    }
}
package com.nonnulldev.underling.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Player(

        @PrimaryKey
        open var Name: String = "",
        open var Level: Int = 0

) : RealmObject()

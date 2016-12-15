package com.nonnulldev.underling.data.remote

import io.reactivex.Observable

interface DataService {
    fun getData() : Observable<String>
}

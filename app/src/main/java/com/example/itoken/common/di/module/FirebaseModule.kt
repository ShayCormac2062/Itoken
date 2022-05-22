package com.example.itoken.common.di.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    private val databaseRoot = "https://itoken-b3e4a-default-rtdb.europe-west1.firebasedatabase.app"
    private val storageRoot = "gs://itoken-b3e4a.appspot.com/"

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseDatabase =
        FirebaseDatabase.getInstance(databaseRoot)

    @Provides
    @Singleton
    fun provideDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.reference

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = Firebase.storage(storageRoot)

}


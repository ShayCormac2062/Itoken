package com.example.itoken.common.di.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DATABASE_ROOT = "https://itoken-b3e4a-default-rtdb.europe-west1.firebasedatabase.app"
private const val STORAGE_ROOT = "gs://itoken-b3e4a.appspot.com/"
@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseDatabase =
        FirebaseDatabase.getInstance(DATABASE_ROOT)

    @Provides
    @Singleton
    fun provideDatabaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.reference

    @Provides
    @Singleton
    fun provideStorage(): FirebaseStorage = Firebase.storage(STORAGE_ROOT)

}


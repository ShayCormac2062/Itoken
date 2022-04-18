package com.example.itoken.common.di.module

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DATABASE_ROOT = "https://itoken-b3e4a-default-rtdb.europe-west1.firebasedatabase.app"

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


}


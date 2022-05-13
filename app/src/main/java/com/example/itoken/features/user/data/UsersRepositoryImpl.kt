package com.example.itoken.features.user.data

import android.net.Uri
import android.util.Log
import com.example.itoken.common.db.dao.GetUserDao
import com.example.itoken.features.user.data.db.dao.UsersDao
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userDatabase: UsersDao,
    private val getUserDatabase: GetUserDao,
    private val firebase: DatabaseReference,
    private val storage: FirebaseStorage,
    private val scope: DispatcherProvider
) : UsersRepository {

    private var currentUser: UserModel? = null

    override suspend fun registerUser(user: UserModel): Boolean {
        return withContext(scope.IO) {
            isUserExists(firebase.child("users"), user)
            if (currentUser == null) {
                if (user.imageUrl?.isNotEmpty() == true) {
                    storage.getReference("${user.stringId}.jpg").apply {
                        putFile(Uri.parse(user.imageUrl)).addOnCompleteListener {
                            if (it.isSuccessful) {
                                downloadUrl.addOnSuccessListener { url ->
                                    user.imageUrl = url.toString()
                                }
                            }
                        }.await()
                    }
                }
                firebase.child("users").child("${user.stringId}").setValue(user)
                userDatabase.add(user.toUser())
                true
            } else false
        }
    }

    override suspend fun addUser(user: UserModel): UserModel {
        currentUser = null
        isUserExists(firebase.child("users"), user)
        if (currentUser != null) {
            currentUser?.toUser()?.let {
                userDatabase.add(it)
            }
            return getUserDatabase.getUser()?.toUserModel() ?: emptyUser()
        }
        return emptyUser()
    }

    override suspend fun signOut() {
        userDatabase.signOut()
    }

    override suspend fun changeBalance(newBalance: Double?) {
        userDatabase.changeBalance(newBalance)
        firebase.child("users")
            .child(getUser()?.stringId.toString())
            .child("balance")
            .setValue(newBalance)
    }

    override suspend fun changePhoto(newPhoto: String?) {
        userDatabase.changePhoto(newPhoto)
        firebase.child("users")
            .child(getUser()?.stringId.toString())
            .child("imageUrl")
            .setValue(newPhoto)
    }

    override suspend fun getUser(): UserModel? = getUserDatabase.getUser()?.toUserModel()

    private suspend fun isUserExists(ref: DatabaseReference, user: UserModel) {
        with(ref.get()) {
            addOnSuccessListener {
                for (dto in it.children) {
                    val newUser = retrieveUser(dto)
                    if ((newUser.nickname == user.nickname ||
                                newUser.email == user.email)
                        && newUser.password == user.password
                    ) {
                        currentUser = newUser
                        break
                    }
                }
            }.await()
            addOnFailureListener {
                Log.e("IN_USER_REPOSITORY_IMPL", it.message.toString())
            }
        }
    }

    private fun retrieveUser(dto: DataSnapshot) = UserModel(
        dto.child("stringId").value as String?,
        dto.child("imageUrl").value as String?,
        dto.child("nickname").value as String?,
        dto.child("description").value as String?,
        dto.child("password").value as String?,
        dto.child("email").value as String?,
        null,
        (dto.child("balance").value as Long).toDouble()
    )

    private fun emptyUser(): UserModel = UserModel(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
    )

}
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
                storage.getReference("${user.stringId}.jpg").apply {
                    putFile(Uri.parse(user.imageUrl)).addOnCompleteListener {
                        if (it.isSuccessful) {
                            downloadUrl.addOnSuccessListener { url ->
                                user.imageUrl = url.toString()
                            }
                        }
                    }.await()
                }
                firebase.child("users").child("${user.stringId}").push().setValue(user)
                userDatabase.add(user.toUser())
                true
            } else false
        }
    }

    override suspend fun addUser(user: UserModel) {
        withContext(scope.IO) {
            isUserExists(firebase.child("users"), user)
            if (currentUser != null) {
                currentUser?.toUser()?.let {
                    userDatabase.add(it)
                }
            }
        }
    }

    override suspend fun signOut() {
        withContext(scope.IO) {
            userDatabase.signOut()
        }
    }

    override suspend fun changeBalance(newBalance: Double?) {
        withContext(scope.Main) {
            userDatabase.changeBalance(newBalance)
            firebase.child("users")
                .child(getUser()?.stringId.toString())
                .child("balance")
                .setValue(newBalance)
        }
    }

    override suspend fun getUser(): UserModel? {
        return withContext(scope.Main) {
            getUserDatabase.getUser()?.toUserModel()
        }
    }

    private fun isUserExists(ref: DatabaseReference, user: UserModel) {
        ref.get().addOnSuccessListener {
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
        }.addOnFailureListener {
            Log.e("IN_USER_REPOSITORY_IMPL", it.message.toString())
        }
    }

    private fun retrieveUser(dto: DataSnapshot) = UserModel(
        dto.child("stringId").value as String?,
        dto.child("imageUrl").value as String?,
        dto.child("nickname").value as String?,
        dto.child("description").value as String?,
        dto.child("password").value as String?,
        dto.child("email").value as String?,
//                    dto.child("bought_assets").value as List<ItemAsset>?,
        null,
        (dto.child("balance").value as Long).toDouble()
    )

}
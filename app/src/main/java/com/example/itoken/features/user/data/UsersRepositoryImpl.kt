package com.example.itoken.features.user.data

import android.util.Log
import com.example.itoken.common.db.dao.GetUserDao
import com.example.itoken.features.user.data.db.dao.UsersDao
import com.example.itoken.features.user.domain.model.ItemAsset
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.utils.DispatcherProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userDatabase: UsersDao,
    private val getUserDatabase: GetUserDao,
    private val firebase: DatabaseReference,
    private val scope: DispatcherProvider
) : UsersRepository {

    private var currentUser: UserModel? = null


    override suspend fun registerUser(user: UserModel): Boolean {
        return withContext(scope.IO) {
            isUserExists(firebase.child("users"), user)
            if (currentUser == null) {
                firebase.child("users").push().setValue(user)
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

    private fun isUserExists(ref: DatabaseReference, user: UserModel) {
        ref.get().addOnSuccessListener {
            for (dto in it.children) {
                val newUser = UserModel(
                    dto.child("stringId").value as String?,
                    dto.child("imageUrl").value as String?,
                    dto.child("nickname").value as String?,
                    dto.child("description").value as String?,
                    dto.child("password").value as String?,
                    dto.child("email").value as String?,
//                    dto.child("assets").value as List<ItemAsset>?,
                    null,
                    (dto.child("balance").value as Long).toDouble()
                )
                if ((newUser.nickname == user.nickname ||
                            newUser.email == user.email)
                    && newUser.password == user.password) {
                    currentUser = newUser
                    break
                }
            }
        }.addOnFailureListener {
            Log.e("IN_USER_REPOSITORY_IMPL", it.message.toString())
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
        }
    }

    override suspend fun getUser(): UserModel? {
        return withContext(scope.Main) {
            getUserDatabase.getUser()?.toUserModel()
        }
    }

}
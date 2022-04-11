package com.example.itoken.features.user.data

import com.example.itoken.features.user.data.db.dao.UsersDao
import com.example.itoken.features.user.domain.model.UserModel
import com.example.itoken.features.user.domain.repository.UsersRepository
import com.example.itoken.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val database: UsersDao,
    private val scope: DispatcherProvider
) : UsersRepository {

    override suspend fun addUser(user: UserModel) {
        withContext(scope.IO) {
            database.add(user.toUser())
        }
    }

    override suspend fun signOut() {
        withContext(scope.IO) {
            database.signOut()
        }
    }

    override suspend fun changeBalance(newBalance: Double?) {
        withContext(scope.Main) {
            database.changeBalance(newBalance)
        }
    }

}
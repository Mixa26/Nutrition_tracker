package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.repository

import io.reactivex.Single
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.UserDao
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.UserEntity

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository{

    override fun insertUsers(users: List<UserEntity>): Single<List<Long>> {
        return userDao.insertUsers(users)
    }

    override fun getByUsername(username: String): Single<UserEntity?> {
        return userDao.getByUsername(username)
    }
}
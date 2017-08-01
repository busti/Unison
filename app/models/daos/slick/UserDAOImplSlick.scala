package models.daos.slick

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import models.daos.UserDAO
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.H2Driver

class UserDAOImplSlick @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends UserDAO with UserTableDefSlick with HasDatabaseConfigProvider[H2Driver] {

  import driver.api._

  def loginInfoQuery(loginInfo: LoginInfo) =
    loginInfos.filter(dbLoginInfo => dbLoginInfo.providerID === loginInfo.providerID && dbLoginInfo.providerKey === loginInfo.providerKey)

  def find(loginInfo: LoginInfo) = {
    /* Comprehension that creates a query doing the following
     */
    val query = for {
      queryLoginInfo <- loginInfoQuery(loginInfo)
      queryUserLoginInfo <- userLoginInfos.filter(_.loginInfoId === queryLoginInfo.id)
      queryUser <- users.filter(_.uuid === queryUserLoginInfo.userId)
    } yield queryUser

    //Executes the query and stores the resulting DBUser and the initial LoginInfo into a User
    db.run(query.result.headOption).map { dbUserOption =>
      dbUserOption.map { user =>
        User(user.uuid, loginInfo, user.username, user.email, user.avatarURL, user.activated)
      }
    }
  }

  def find(uuid: UUID) = {
    val query = for {
      queryUser <- users.filter(_.uuid === uuid)
      queryUserLoginInfo <- userLoginInfos.filter(_.userId === queryUser.uuid)
      queryLoginInfo <- loginInfos.filter(_.id === queryUserLoginInfo.loginInfoId)
    } yield (queryUser, queryLoginInfo)

    db.run(query.result.headOption).map { resultOption =>
      resultOption.map {
        case (user, loginInfo) =>
          User(
            user.uuid,
            LoginInfo(loginInfo.providerID, loginInfo.providerKey),
            user.username, user.email, user.avatarURL, user.activated
          )
      }
    }
  }

  def save(user: User) = {
    val dbUser = DBUser(user.uuid, user.username, user.email, user.avatarURL, user.activated)
    val dBLoginInfo = DBLoginInfo(None, user.loginInfo.providerID, user.loginInfo.providerKey)

    val loginInfoAction = {
      val retrieveLoginInfo = loginInfos.filter(
        info =>
          info.providerID === user.loginInfo.providerID
            &&
            info.providerKey === user.loginInfo.providerKey
      ).result.headOption

      val insertLoginInfo = loginInfos.returning(loginInfos.map(_.id)).into(
        (info, id) =>
          info.copy(id = Some(id))
      ) += dBLoginInfo

      for {
        loginInfoOption <- retrieveLoginInfo
        loginInfo <- loginInfoOption.map(DBIO.successful).getOrElse(insertLoginInfo)
      } yield loginInfo
    }

    val actions = (
      for {
        _ <- users.insertOrUpdate(dbUser)
        loginInfo <- loginInfoAction
        _ <- userLoginInfos += DBUserLoginInfo(dbUser.uuid, loginInfo.id.get)
      } yield ()
      ).transactionally

    db.run(actions).map(_ => user)
  }
}
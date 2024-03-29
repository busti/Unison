package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}

/**
  * The user object.
  *
  * @param uuid      The unique ID of the user.
  * @param loginInfo The linked login info.
  * @param username  The nickname the user has chosen for themselves.
  *                  This should be unique amongst all users but can be changed at any time.
  * @param email     The email of the authenticated provider.
  * @param avatarURL Maybe the avatar URL of the authenticated provider.
  * @param activated Whether the user has activated their account using their E-Mail Address or not.
  */
case class User(
  uuid: UUID,
  loginInfo: LoginInfo,
  username: String,
  email: String,
  avatarURL: Option[String],
  activated: Boolean
) extends Identity
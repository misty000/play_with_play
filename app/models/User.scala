package models

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午4:42
 */
object User {
	def find(u: String): Option[User] = Option(User(u))
}

case class User(name: String) {
}

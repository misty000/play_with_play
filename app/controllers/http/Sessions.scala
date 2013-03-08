package controllers.http

import play.api.mvc.{Action, Controller}

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午3:35
 */
object Sessions extends Controller {
	def session1 = Action {
		request => {
			request.session.get("connected") map {
				user => Ok(s"Hello $user")
			} getOrElse {
				Unauthorized("Oops, you are not connected")
			}
		}
	}

	def session2 = Action {
		implicit request => {
			session.get("connected") map {
				user => Ok(s"Hello $user")
			} getOrElse {
				Unauthorized("Opps, you are not connected")
			}
		}
	}

	def session3 = Action {
		implicit request =>
		//追加元素
			Ok("Hello world!").withSession(session + ("saidHello" -> "yes"))
	}

	def session4 = Action {
		implicit request =>
		//替换整个session
			Ok("Hello world!").withSession("connected" -> "user@gmail.com")
	}

	def session5 = Action {
		implicit request =>
		//删除元素
			Ok("Hello world!").withSession(session - "theme")
	}

	def session6 = Action {
		implicit request =>
		//丢弃整个session
			Ok("Hello world!").withNewSession
	}
}

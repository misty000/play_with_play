package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午2:35
 */
object Users extends Controller {
	def show(id: Long) = Action {
		Ok(s"show user [$id]")
	}
}

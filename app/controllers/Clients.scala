package controllers

import play.api.mvc.{Action, Controller}

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午2:05
 */
object Clients extends Controller {
	def list() = Action {
		Ok("There's manay clients list here.")
	}

	def list(page: Int) = Action {
		Ok(s"List Clients page [$page]")
	}

	def show(id: Long) = Action {
		Ok(s"show client [$id]")
	}

	//	def show(id: String) = Action {
	//		Ok(s"show client with string [$id]")
	//	}
}

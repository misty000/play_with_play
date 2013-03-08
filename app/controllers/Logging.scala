package controllers

import play.api.mvc.{Result, Request, Action}
import play.api.Logger

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午4:31
 */
case class Logging[A](action: Action[A]) extends Action[A] {
	def apply(request: Request[A]): Result = {
		Logger.info("Calling action")
		action(request)
	}

	lazy val parser = action.parser
}

package controllers.http

import play.api.mvc._
import play.api.Logger
import models.User

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午4:19
 */
object ActionComposition extends Controller {
	def LoggingAction(f: Request[AnyContent] => Result): Action[AnyContent] = {
		Action(request => {
			Logger.info("Calling action")
			f(request)
		})
	}

	def LoggingAction[A](bp: BodyParser[A])(f: Request[A] => Result): Action[A] = {
		Action(bp)(request => {
			Logger.info("Calling action")
			f(request)
		})
	}

	def Logging[A](action: Action[A]): Action[A] = {
		Action(action.parser)(request => {
			Logger.info("Calling action")
			action(request)
		})
	}

	def index = LoggingAction(request => {
		Ok("Hello World")
	})

	def index2 = LoggingAction(parse.text)(request => {
		Ok("Hello world")
	})

	def index3 = Logging {
		Action {
			Ok("Hello world")
		}
	}

	def index4 = Logging {
		Action(parse.text)(request => {
			Ok("Hello world")
		})
	}

	def CustomAuthenticated[A](action: User => Action[A]): Action[A] = {
		def getUser(request: RequestHeader): Option[User] = {
			println("getUser")
			request.session.get("user").flatMap(u => User.find(u))
		}
		val authenticatedBodyParser = parse.using(request => {
			println("authenticatedBodyParser")
			getUser(request).map(u => action(u).parser) getOrElse {
				parse.error(Unauthorized)
			}
		})
		Action(authenticatedBodyParser)(request => {
			println("Action")
			getUser(request) map {
				u => action(u)(request)
			} getOrElse {
				Unauthorized
			}
		})
	}

	def index5 = CustomAuthenticated(user => {
		Action(request => {
			Ok(s"Hello ${user.name}")
		})
	})

	import Security._

	def index6 = Authenticated(s => {
		Action {
			Ok("")
		}
	})

	def index7 = {
		//in a Security trait
		def username(request: RequestHeader) = request.session.get("email")
		def onUnauthorized(request: RequestHeader) = Results.Redirect("")
		Authenticated(username, onUnauthorized)(user => {
			Action(implicit request => {
				Ok("Hello " + user)
			})
		})
	}

	def index8 = Authenticated(user => {
		Action(implicit req => {
			Ok("")
		})
	})

	def AnotherAuthenticated(f: (User, Request[AnyContent]) => Result) = {
		Action(req => {
			req.session.get("user").flatMap(u => User.find(u)) map {
				user => f(user, req)
			} getOrElse Unauthorized
		})
	}

	def index9 = AnotherAuthenticated((user, req) => {
		Ok(s"Hello ${user.name}")
	})

	def AnotherAuthenticated2(f: User => Request[AnyContent] => Result) = {
		Action(req => {
			req.session.get("user").flatMap {
				u => User.find(u)
			} map {
				user => f(user)(req)
			} getOrElse {
				Unauthorized
			}
		})
	}

	def index10 = AnotherAuthenticated2(user => implicit req => {
		Ok(s"Hello ${user.name}")
	})

	def Authenticated2(f: AuthenticatedRequest2 => Result) = Action(req => {
		req.session.get("user").flatMap(u => User.find(u)) map {
			user => f(AuthenticatedRequest2(user, req))
		} getOrElse {
			Unauthorized
		}
	})

	def index11 = Authenticated2(implicit req => {
		Ok(s"Hello ${req.user.name}")
	})

	def Authenticated3[A](p: BodyParser[A])(f: AuthenticatedRequest3[A] => Result) = Action(p)(req => {
		req.session.get("user") flatMap (u => User.find(u)) map {
			user => f(AuthenticatedRequest3(user, req))
		} getOrElse {
			Unauthorized
		}
	})

	def Authenticated3(f: AuthenticatedRequest3[AnyContent] => Result): Action[AnyContent] = {
		Authenticated3(parse.anyContent)(f)
	}
}

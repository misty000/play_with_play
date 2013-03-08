package controllers.asynhttp

import play.api.mvc.{Action, Controller}
import concurrent.Future
import play.api.libs.concurrent.Promise
import concurrent.duration.DurationDouble

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午9:02
 */
object AsyncResult extends Controller {

	import concurrent.ExecutionContext.Implicits.global

	def intensiveComputation(): Int = ???

	def index = Action {
		val futureInt = Future {
			intensiveComputation()
		}
		Async {
			futureInt map (i => {
				Ok(s"Got result: $i")
			})
		}
	}

	def timeout = Action {
		val fInt = Future {
			intensiveComputation()
		}
		val timeout = Promise.timeout("Oops", 2.seconds)
		Async {
			Future.firstCompletedOf(Seq(fInt, timeout)) map {
				case t: String => InternalServerError(t)
				case i: Int => Ok(s"Got result: $i")
			}
		}
	}
}

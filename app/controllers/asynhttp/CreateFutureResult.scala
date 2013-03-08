package controllers.asynhttp

import play.api.mvc.{Result, Controller}
import concurrent.Future
import play.api.libs.concurrent.Akka

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午8:53
 */
object CreateFutureResult extends Controller {

	import concurrent.ExecutionContext.Implicits.global

	def computePIAsynchronously() = ???

	def intensiveComputation() = ???

	val futurePIValue: Future[Double] = computePIAsynchronously()

	val futureResult: Future[Result] = futurePIValue.map(pi => {
		Ok(s"PI value computed: $pi")
	})

	val futureInt: Future[Int] = Future {
		intensiveComputation()
	}

	import play.api.Play.current
	val futureOfInt: Future[Int] = Akka.future {
		intensiveComputation()
	}
}

package controllers

import play.api.mvc._

object Application extends Controller {
	//默认使用utf-8
	implicit val myCustomCharset = Codec.iso_8859_1

	def index = Action {
		Ok(views.html.index("My new application is ready."))
	}

	def download(name: String) = Action {
		Ok(s"you will download $name")
	}

	def show(page: String) = Action {
		Ok(s"show page [$page]").as("text/html")
	}

	val echo = Action {
		request =>
			Ok(s"Got request [$request]")
	}

	def hello(name: String) = Action {
		Ok(<h1>Hello
			{name}
		</h1>).as(HTML)
	}

	def helloBob = Action {
		Redirect(routes.Application.hello("Bob"))
	}

	def helloworld = Action {
		Ok("Hello World!").withHeaders(CACHE_CONTROL -> "max-age=3600", ETAG -> "xx")
			.withCookies(Cookie("theme", "blue"))
			.discardingCookies(DiscardingCookie("theme"))
	}

	val implicitRequest = Action {
		implicit request => {
			Ok(s"Got request [$request]")
		}
	}

	val parseJson = Action(parse.json) {
		implicit request => {
			Ok(s"Got requset [$request]")
		}
	}

	def todo(name: String) = TODO

	//	val ok = Ok("Hello world!")
	//	val notFound = NotFound
	//	val pageNotFound = NotFound(<h1>Page not found</h1>)
	//	val badRequest = BadRequest(views.html.form(formWithErrors))
	//	val oops = InternalServerError("Oops")
	//	val anyStatus = Status(488)("Strange response type")
	//	val redirect = Redirect("/user/home", status = MOVED_PERMANENTLY)
}
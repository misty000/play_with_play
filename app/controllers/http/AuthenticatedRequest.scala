package controllers.http

import models.User
import play.api.mvc.{AnyContent, WrappedRequest, Request}

/**
 * Created with IntelliJ IDEA.
 * User: Misty
 * Date: 13-3-7
 * Time: 下午8:13
 */
case class AuthenticatedRequest2(user: User, request: Request[AnyContent])
	extends WrappedRequest(request)

case class AuthenticatedRequest3[A](user: User, request: Request[A])
	extends WrappedRequest(request)


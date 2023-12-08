package ba.sake.hepek.zio_example

import zio._
import zio.http._
import zio.http.endpoint._
import ba.sake.hepek.zio.*
import ba.sake.hepek.zio_example.views.IndexView
import ba.sake.hepek.zio_example.views.CreateCustomerView

object MainApp extends ZIOAppDefault {
  override def run =
    Console.printLine("please visit http://localhost:7710") *>
      Server.serve(routes).provide(Server.defaultWithPort(7710))

  private val routes = (
    makeWebApp ++
      makeWebAssets ++
      makeNotFound
  )
    .catchAllCauseZIO(_ =>
      ZIO.succeed(
        Response.html(
          IndexView,
          Status.InternalServerError,
        ),
      ),
    )

  private def makeWebApp: App[Any] =
    Http.collectZIO[Request] {
      case Method.GET -> !! =>
        ZIO.succeed(Response.html(IndexView))

      case Method.GET -> !! / "showForm" =>
        ZIO.succeed(Response.html(CreateCustomerView()))

      case req @ Method.POST -> !! / "submitForm" =>
        // https://github.com/zio/zio-http/blob/v3.0.0-RC1/zio-http-example/src/main/scala/example/MultipartFormData.scala
        req
          .body
          .asURLEncodedForm
          .mapError(ex =>
            Response(
              Status.InternalServerError,
              body =
                Body.fromString(s"Failed to decode body as multipart/form-data (${ex.getMessage}"),
            ),
          )
          .map { form =>
            Response.text(s"Got form data ${form.map}")
          }
    }

  def makeWebAssets: Http[Any, Throwable, Request, Response] =
    Http.collectHttp[Request] {
      case Method.GET -> !! / "assets" / asset =>
        Http.fromResource(asset)
    }

  private def makeNotFound =
    Http.collect[Request] {
      case req =>
        Response.html(
          IndexView,
          Status.NotFound,
        )
    }
}

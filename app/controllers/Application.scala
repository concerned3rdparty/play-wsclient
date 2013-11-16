package controllers

import java.net._
import java.lang._
import play.api._
import play.api.mvc._
import org.jboss.jbossas.quickstarts.wshelloworld.helloworld.HelloWorldService_Service
import play.api.libs.json._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import models._

object Application extends Controller {

  private def sayHello: String = {
    var s: HelloWorldService_Service = null
    try {
      s = new HelloWorldService_Service(new java.net.URL("http://localhost:8080/jboss-as-helloworld-ws?wsdl"))
    } catch {
      case e: MalformedURLException => {
        throw new RuntimeException(e)
      }
    }
    return s.getHelloWorld.sayHello
  }

  case class Message(content: String, counter: Int)

  implicit object MessageFormat extends Format[Message] {
    def writes(o: Message): JsValue = JsObject(
      List("counter" -> JsNumber(o.counter),
        "content" -> JsString(o.content)))

    def reads(json: JsValue): Message = Message(
      (json \ "content").as[String],
      (json \ "counter").as[Int])
  }

  def index = Action {
    val m = Message(sayHello, 1)
    Ok(Json.toJson(m))
    // Ok(views.html.index("Your new application is ready."))
  }

}

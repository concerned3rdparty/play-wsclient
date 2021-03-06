import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "wsclient"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "sample" % "resteasy-sample" % "1.0-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here  
      resolvers += (
	    "Local Maven Repository" at "file://" + "/home/bkhadige" + "/.m2/repository"
      )    
    )

}

package com.github.namchid.app

import org.scalatra._
import scalate.ScalateSupport
import scala.xml.{ Text, Node }

object Page {

  def set(title: String, contents: Seq[Node], containerClassName: String = "outerContainer") = {
    <html>
      <head lang="en">
        <title>{ title }</title>
      </head>
      <body>
        <div id="navContainer">
        </div>
        <div class={ containerClassName }>
          { contents }
        </div>
      </body>
    </html>
  }
}

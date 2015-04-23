package com.github.namchid.app

import Page.set

class ScalatraTaskManagerServlet extends ScalatraTaskManagerWebAppStack {

  get("/") {
    session.invalidate()
    val contents = {
      <form action={ url("/tasks") } method='POST'>
        <table>
          <tr>
            <td class='right'>username</td>
            <td><input type='text' name='username'/></td>
          </tr>
          <tr>
            <td class='right'>password</td>
            <td><input type='password' name='password'/></td>
          </tr>
          <tr>
            <td colspan='2' id='centered'><input type='submit'/></td>
          </tr>
        </table>
      </form>
    }
    set("Scalatra Task Manager Login", contents, "noclass")
  }

  post("/tasks") {
    val username = params("username")
    val password = params("password")
    val contents = {
      <h1>You posted:</h1>
      <h2>{ username }</h2>
      <h2>{ password }</h2>
    }
    set("Scalatra Task Manager", contents, "none")
  }
  
  

}


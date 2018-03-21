package io.github.gitbucket.helloworld.controller

import gitbucket.core.controller.ControllerBase

class HelloWorldController extends ControllerBase {

  get("/:owner/:repository/jenkinsPickerSetting") {
    "jenkinsPickerSetting!!!!!!ほげ"
  }

}

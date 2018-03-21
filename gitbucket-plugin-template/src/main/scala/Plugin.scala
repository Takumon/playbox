import javax.servlet.ServletContext

import gitbucket.core.controller.Context
import gitbucket.core.plugin._
import gitbucket.core.service.RepositoryService.RepositoryInfo
import gitbucket.core.service.SystemSettingsService.SystemSettings
import gitbucket.core.service._
import io.github.gitbucket.helloworld.controller.HelloWorldController
import io.github.gitbucket.solidbase.model.Version

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "helloworld"
  override val pluginName: String = "HelloWorld Plugin"
  override val description: String = "First example of GitBucket plug-in"
  override val versions: List[Version] = List(new Version("1.0.0"))

  override val controllers = Seq(
    "/*" -> new HelloWorldController()
  )


  override val repositoryMenus = Seq(
    (repository: RepositoryInfo, context: Context) =>
      Some(Link(
        id = pluginId,
        label = pluginName,
        path = s"/jenkinsPickerSetting",
        icon = Some("menu-icon octicon octicon-graph")
      ))
  )

  override def javaScripts(registry: PluginRegistry, context: ServletContext, settings: SystemSettings): Seq[(String, String)] = {
    val path = settings.baseUrl.getOrElse(context.getContextPath)
    Seq(
      ".*/(?!.*(signin|dashboard|admin)).+/.+" -> s"""
       |</script>
       |<script>
       |  alert("こんにちは")
       |</script>
       |<script>
       """.stripMargin
    )
  }

  override val pullRequestHooks = Seq(
    new JenkinsHook with PullRequestService with IssuesService with AccountService with RepositoryService with CommitsService
  )

  // override val issueSidebars = Seq(
  //   (issue: Issue, repositoryInfo: RepositoryInfo, context: Context) => {
  //     Some(Html())
  //   }
  // )

  // override val suggestionProviders = Seq(
  //   new SuggestionProvider {
  //     override val id: String = "Jenkins"
  //     override val prefix: String = "-"
  //     override val context: Seq[String] = Seq("issues")
  //     override def values(repository: RepositoryInfo): Seq[String] = Seq("Jenkins?")
  //   }
  // )
}

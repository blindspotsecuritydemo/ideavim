package _Self.subprojects

import _Self.buildTypes.Release
import _Self.buildTypes.ReleaseDev
import _Self.buildTypes.ReleaseEap
import jetbrains.buildServer.configs.kotlin.v2019_2.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Releases : Project({
  name = "IdeaVim releases"
  description = "Stable and EAP releases for IdeaVim"

  params {
    password(
      "env.CERTIFICATE_CHAIN",
      "credentialsJSON:1bab4a88-10e7-4bf9-856c-e6253499dc95",
      display = ParameterDisplay.HIDDEN
    )
    password(
      "env.PRIVATE_KEY_PASSWORD",
      "credentialsJSON:7c12c867-fe09-4a2f-884d-6fd0ec0a1e79",
      display = ParameterDisplay.HIDDEN
    )
    password(
      "env.PRIVATE_KEY",
      "credentialsJSON:5d8b553d-fd7e-4347-abd2-51d8d0f2b3f7",
      display = ParameterDisplay.HIDDEN
    )
  }

  buildType(Release)
  buildType(ReleaseEap)
  buildType(ReleaseDev)
})

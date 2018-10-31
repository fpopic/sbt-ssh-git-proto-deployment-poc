lazy val githubRepoHostingProtobuf = RootProject(
  build = uri("ssh://git@github.com/fpopic/github-repo-hosting-protobuf.git")
)

lazy val anotherProject = RootProject(
  build = uri("ssh://git@github.com/fpopic/github-repo-hosting-protobuf.git#another_branch_with_same_proto_of_diff_version")
)

lazy val root = (project in file("."))
  .dependsOn(githubRepoHostingProtobuf, anotherProject)
  .settings(
    name := "sbt-ssh-git-proto-deployment-poc",
    organization := "com.fpopic.github",
    version := "0.1",
    scalaVersion := "2.12.7",

    // INPUT
    PB.protoSources in Compile ++= Seq(
      // Specify location of .proto files from project dependency
      baseDirectory.in(anotherProject).value / "my-protobuf-files",
      baseDirectory.in(githubRepoHostingProtobuf).value / "my-protobuf-files",
      // To be able to pickup .proto files from .jar dependency
      // e.g. libraryDependencies += "organization" %% "name" % "version" % "protobuf"
      target.value / "protobuf_external"
    ),

    // SETTINGS
    excludeFilter in PB.generate := new ExactFilter("car.proto"),

    // OUTPUT
    PB.targets in Compile := Seq(
      // Generate Java proto classes
      PB.gens.java -> (sourceManaged in Compile).value,
      // Gemerate Scala proto case classes
      scalapb.gen(asciiFormatToString = true, javaConversions = true) -> (sourceManaged in Compile).value
    )
  )

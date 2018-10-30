### sbt-ssh-git-proto-deployment-PoC

This is a Proof of Concept project that demonstrates how to compile protobuf file
that depends on another external protobuf file that lives in another [git repository](https://github.com/fpopic/github-repo-hosting-protobuf).

Requirements:
1.  [sbt](https://www.scala-sbt.org/download.html) 
2.  [sbt-protoc plugin](project/protoc.sbt)
3.  SSH key (in `~/.ssh/`) to access private git repository

sbt & git:
1. If you need to reference a sbt sub-project from the github repository:
    ```scala
      lazy val subProject = ProjectRef(
        build = uri("ssh://git@github.com/<user>/<repo>.git#<branch|commit|tag>"),
        project= "sub-project"
      )
    ```
2. Else just specify github repository:
    ```scala
      lazy val protoTracking = RootProject(
        build = uri("ssh://git@github.com/<user>/<repo>.git#<branch|commit|tag>")
      )
    ```

Dependency projects will be cloned/checkouted to:  `~/.sbt/<sbt version>/staging/<sha>/<repo>/`


Run:
1. ```git clone```
2. ```sbt```
3. ```compile```

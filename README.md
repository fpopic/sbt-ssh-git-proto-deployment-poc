### sbt-ssh-git-proto-deployment-PoC

This is a Proof of Concept project that demonstrates how to compile protobuf file
that depends on another external protobuf file that lives in another [git repository](https://github.com/fpopic/github-repo-hosting-protobuf).

#### 1. Requirements:
1.  [sbt](https://www.scala-sbt.org/download.html)
2.  [sbt-protoc plugin](project/protoc.sbt) to compile .proto files to .java .scala classes
3.  SSH key (in `~/.ssh/`) to access private git repository

#### 2. Set build.sbt
-   If you need to reference a sbt sub-project from the github repository:
    ```scala
    lazy val sbtProjectFromOtherGitRepo = ProjectRef(
      build = uri("ssh://git@github.com/<user>/<repo>.git#<branch|commit|tag>"),
      project = "sub-project"
    )
    ```
-   Else just specify github repository:
    ```scala
    lazy val projectFromOtherGitRepo = RootProject(
      build = uri("ssh://git@github.com/<user>/<repo>.git#<branch|commit|tag>")
    )
    ```
Dependency projects will be cloned/checkouted to:  `~/.sbt/<sbt version>/staging/<sha>/<repo>/`

-   Then using `sbt-protoc` specify location of .proto files from project dependency:
    ```scala
    PB.protoSources in Compile ++= Seq(
      baseDirectory.in(projectFromOtherGitRepo).value / "path/to/folder/where/protos/are"
    )
    ```
    
#### 3. Run:
1. ```git clone git@github.com:fpopic/sbt-ssh-git-proto-deployment-poc.git```
2. ```cd sbt-ssh-git-proto-deployment-poc```
2. ```sbt```
3. ```compile```


#### 4. Notes:
Dependency projects are not getting updated using `update` command after they have been changed in git, 
that's reason why is better to hardcode commit/tag version of your git repository dependency.
- Workaround is to delete sbt staging folder before update so the project will always clone from git
    ```bash
    rm -r ~/.sbt/<sbt version>/staging/
    sbt update 
    ```
    You can maybe create a sbt external task and name it [cleanStagingAndUpdate](https://groups.google.com/forum/#!topic/simple-build-tool/YJnUNSjrU6Q)
- Or use sbt for downloading git repos and then ```publishLocal```  to get .jar and add jar to ```libraryDependencies``` instead of dependsOn(project)
    - https://github.com/sbt/sbt/issues/1284
    - https://stackoverflow.com/questions/22432666/how-can-sbt-project-import-library-from-github-cloned-to-local-directory

This PoC is made for CI automatization (Jenkins) for deploying proto files to maven repository (every time cloning from scratch)

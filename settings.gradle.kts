pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

//    versionCatalogs {
//        create("libs") {
//            from(files("libs.versions.toml"))
//        }
//    }
}


rootProject.name= "WebsDemo"

include (":app")
include (":jscall")
include (":wslib")
include (":netstatelib")
include (":utils")


include(":navs")
include(":dbfile")
include(":app:net")

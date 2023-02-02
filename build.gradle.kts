subprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")

    group = "hao"

    repositories {
        mavenCentral()
    }

    // versions
    val verJunitJupiter by extra("5.9.2")
    val verMockito by extra("5.1.1")

    // dependencies
    val depJunitJupiterApi by extra("org.junit.jupiter:junit-jupiter-api:${verJunitJupiter}")
    val depJunitJupiterEngine by extra("org.junit.jupiter:junit-jupiter-engine:${verJunitJupiter}")
    val depMockitoCore by extra("org.mockito:mockito-core:${verMockito}")
    val depMockitoJunitJupiter by extra("org.mockito:mockito-junit-jupiter:${verMockito}")

    val testImplementation by configurations
    val testRuntimeOnly by configurations

    dependencies {
        testImplementation(depJunitJupiterApi)
        testImplementation(depMockitoCore)
        testImplementation(depMockitoJunitJupiter)
        testRuntimeOnly(depJunitJupiterEngine)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

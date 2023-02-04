plugins {
    `java-test-fixtures`
}

val depJunitJupiterApi: String by extra

dependencies {
    testFixturesImplementation(depJunitJupiterApi)
}

tasks.withType<Test> {
    systemProperty("demitasse.core.CoreToolsTest.load_key", "demitasse.core.CoreToolsTest\$EB")
}

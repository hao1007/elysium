plugins {
    `java-test-fixtures`
}

val depJunitJupiterApi: String by extra
val depMockitoCore: String by extra

dependencies {
    testFixturesImplementation(depJunitJupiterApi)
    testFixturesImplementation(depMockitoCore)
}

plugins {
    `java-test-fixtures`
}

val depMockitoCore: String by extra

dependencies {
    implementation(project(":demitasse:core"))
    testFixturesImplementation(depMockitoCore)
}

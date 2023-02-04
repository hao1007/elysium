dependencies {
    implementation(project(":demitasse:core"))
    implementation(project(":demitasse:sys"))
    testImplementation(testFixtures(project(":demitasse:sys")))
}

tasks.withType<Test> {
    systemProperty("demitasse.sys", "demitasse.sys.SysFixture")
}

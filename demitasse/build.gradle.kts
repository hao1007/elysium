plugins {
    `jacoco-report-aggregation`
}

dependencies {
    jacocoAggregation(project(":demitasse:core"))
}

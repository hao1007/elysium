plugins {
    `jacoco-report-aggregation`
}

dependencies {
    jacocoAggregation(project(":demitasse:core"))
    jacocoAggregation(project(":demitasse:sys"))
    jacocoAggregation(project(":demitasse:trace"))
}

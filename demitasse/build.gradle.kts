plugins {
    `jacoco-report-aggregation`
}

dependencies {
    jacocoAggregation(project(":demitasse:core"))
    jacocoAggregation(project(":demitasse:prop"))
    jacocoAggregation(project(":demitasse:sys"))
    jacocoAggregation(project(":demitasse:trace"))
}

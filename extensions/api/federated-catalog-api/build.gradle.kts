/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

plugins {
    `java-library`
    id("io.swagger.core.v3.swagger-gradle-plugin")
}

dependencies {
    api(project(":spi:federated-catalog-spi"))
    api(libs.edc.spi.core)
    implementation(libs.edc.spi.transform)
    implementation(libs.edc.spi.web)

    implementation(libs.edc.api.management.config)
    runtimeOnly(libs.edc.spi.jsonld)
    runtimeOnly(libs.edc.json.ld.lib)

    // required for integration test
    testImplementation(libs.edc.spi.dsp.http)
    testImplementation(libs.edc.lib.boot)
    testImplementation(testFixtures(project(":core:federated-catalog-core"))) // provides the TestUtil
    testImplementation(libs.edc.junit)
    testImplementation(libs.edc.core.connector)
    testImplementation(libs.edc.ext.http)
    testImplementation(libs.restAssured)
    testImplementation(libs.edc.iam.mock)
    testImplementation(libs.edc.json.ld.lib)
    testImplementation(libs.edc.dsp.transform.catalog)
    testImplementation(libs.edc.dsp.transform.catalog)

}

edcBuild {
    swagger {
        apiGroup.set("management-api")
    }
}

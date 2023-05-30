/*
 *  Copyright (c) 2020, 2021 Microsoft Corporation
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

package org.eclipse.edc.catalog.node.directory.azure;

import dev.failsafe.RetryPolicy;
import org.eclipse.edc.azure.cosmos.CosmosClientProvider;
import org.eclipse.edc.azure.cosmos.CosmosDbApiImpl;
import org.eclipse.edc.catalog.node.directory.azure.model.FederatedCacheNodeDocument;
import org.eclipse.edc.catalog.spi.FederatedCacheNodeDirectory;
import org.eclipse.edc.runtime.metamodel.annotation.Extension;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.runtime.metamodel.annotation.Provides;
import org.eclipse.edc.spi.security.Vault;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;
import org.eclipse.edc.spi.system.health.HealthCheckService;
import org.eclipse.edc.spi.types.TypeManager;

/**
 * Provides a persistent implementation of the {@link FederatedCacheNodeDirectory} using CosmosDB.
 */
@Provides(FederatedCacheNodeDirectory.class)
@Extension(value = CosmosFederatedCacheNodeDirectoryExtension.NAME)
public class CosmosFederatedCacheNodeDirectoryExtension implements ServiceExtension {

    public static final String NAME = "CosmosDB Federated Cache Node Directory";
    @Inject
    private Vault vault;

    @Inject
    private CosmosClientProvider clientProvider;

    @Inject
    private TypeManager typeManager;

    @Inject
    private RetryPolicy<Object> retryPolicy;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void initialize(ServiceExtensionContext context) {
        var configuration = new FederatedCacheNodeDirectoryCosmosConfig(context);

        var cosmosDbApi = new CosmosDbApiImpl(configuration, clientProvider.createClient(vault, configuration));

        var directory = new CosmosFederatedCacheNodeDirectory(cosmosDbApi, configuration.getPartitionKey(), typeManager, retryPolicy);
        context.registerService(FederatedCacheNodeDirectory.class, directory);

        typeManager.registerTypes(FederatedCacheNodeDocument.class);

        context.getService(HealthCheckService.class).addReadinessProvider(() -> cosmosDbApi.get().forComponent(name()));
    }

}

